package com.east.sea.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.east.sea.mapper.sys.SysMenuMapper;
import com.east.sea.pojo.dto.sys.MenuNodeDTO;
import com.east.sea.pojo.dto.sys.PermScanRequestDTO;
import com.east.sea.pojo.entity.sys.SysMenuEntity;
import com.east.sea.pojo.vo.sys.PermItemVO;
import com.east.sea.pojo.vo.sys.PermScanResultVO;
import com.east.sea.service.SysPermScanService;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class SysPermScanServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements SysPermScanService {

    @Resource
    private RequestMappingHandlerMapping handlerMapping;

    private static final Pattern AUTH_PATTERN = Pattern.compile("hasAuthority\\(['\\\"]([\\w:\\-]+)['\\\"]\\)");

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PermScanResultVO scanAndMerge(PermScanRequestDTO request) {
        Set<String> perms = new LinkedHashSet<>();
        List<PermItemVO> items = new ArrayList<>();

        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo info = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();
            Method method = handlerMethod.getMethod();

            String perm = resolvePermFromAnnotations(handlerMethod);
            String httpMethod = resolveHttpMethod(info);
            String fullPath = resolvePath(info);
            if (!StringUtils.hasText(perm)) {
                continue;
            }
            perms.add(perm);
            items.add(new PermItemVO(perm, httpMethod, fullPath,
                    handlerMethod.getBeanType().getSimpleName(), method.getName()));
        }

        int inserted = 0;
        int updated = 0;
        if (Boolean.TRUE.equals(Boolean.TRUE.equals(request.getPersist()))) {
            Map<String, Long> menuKeyIdMap = upsertManifestMenus(request.getManifest());
            Long defaultParentId = request.getDefaultParentId() == null ? 0L : request.getDefaultParentId();
            for (String perm : perms) {
                SysMenuEntity exist = getOne(new LambdaQueryWrapper<SysMenuEntity>()
                        .eq(SysMenuEntity::getPerms, perm));
                if (exist == null) {
                    SysMenuEntity f = new SysMenuEntity();
                    f.setType("F");
                    f.setPerms(perm);
                    f.setMenuName(suggestName(perm));
                    f.setParentId(resolveParentIdForF(menuKeyIdMap, perm, defaultParentId));
                    f.setSort(0);
                    boolean ok = save(f);
                    if (ok) inserted++;
                } else {
                    Long newParentId = resolveParentIdForF(menuKeyIdMap, perm, exist.getParentId() == null ? defaultParentId : exist.getParentId());
                    boolean needUpdate = false;
                    if (!Objects.equals(exist.getParentId(), newParentId)) {
                        exist.setParentId(newParentId);
                        needUpdate = true;
                    }
                    if (!"F".equals(exist.getType())) {
                        exist.setType("F");
                        needUpdate = true;
                    }
                    if (!StringUtils.hasText(exist.getMenuName())) {
                        exist.setMenuName(suggestName(perm));
                        needUpdate = true;
                    }
                    if (needUpdate) {
                        boolean ok = updateById(exist);
                        if (ok) updated++;
                    }
                }
            }
        }

        PermScanResultVO vo = new PermScanResultVO();
        vo.setFound(perms.size());
        vo.setInserted(inserted);
        vo.setUpdated(updated);
        vo.setItems(items);
        return vo;
    }

    private String resolvePermFromAnnotations(HandlerMethod handlerMethod) {
        org.springframework.security.access.prepost.PreAuthorize preAuthorize =
                AnnotatedElementUtils.findMergedAnnotation(handlerMethod.getMethod(), org.springframework.security.access.prepost.PreAuthorize.class);
        if (preAuthorize == null) {
            preAuthorize = AnnotatedElementUtils.findMergedAnnotation(handlerMethod.getBeanType(), org.springframework.security.access.prepost.PreAuthorize.class);
        }
        if (preAuthorize != null && StringUtils.hasText(preAuthorize.value())) {
            Matcher m = AUTH_PATTERN.matcher(preAuthorize.value());
            if (m.find()) {
                return m.group(1);
            }
        }
        return null;
    }

    private String resolveHttpMethod(RequestMappingInfo info) {
        Set<RequestMethod> methods = info.getMethodsCondition().getMethods();
        if (methods == null || methods.isEmpty()) return "GET";
        return methods.iterator().next().name();
        }

    private String resolvePath(RequestMappingInfo info) {
        try {
            Set<String> values = info.getPatternValues();
            if (values != null && !values.isEmpty()) return values.iterator().next();
        } catch (Throwable ignore) {
        }
        try {
            org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMethodMappingNamingStrategy strategy = null;
        } catch (Throwable ignore) {
        }
        try {
            Object condition = info.getPatternsCondition();
            if (condition != null) {
                @SuppressWarnings("unchecked")
                Set<String> patterns = (Set<String>) condition.getClass().getMethod("getPatterns").invoke(condition);
                if (patterns != null && !patterns.isEmpty()) return patterns.iterator().next();
            }
        } catch (Throwable ignore) {
        }
        return "/";
    }

    private String derivePermByConvention(String path, String httpMethod) {
        if (!StringUtils.hasText(path)) return null;
        String trimmed = path.startsWith("/") ? path.substring(1) : path;
        String[] segs = trimmed.split("/");
        if (segs.length == 0) return null;
        String prefix = segs[0];
        String action = mapHttpMethodToAction(httpMethod);
        if (!StringUtils.hasText(action)) return null;
        return prefix + ":" + action;
    }

    private String mapHttpMethodToAction(String method) {
        if (!StringUtils.hasText(method)) return null;
        switch (method) {
            case "GET": return "view";
            case "POST": return "add";
            case "PUT": return "edit";
            case "DELETE": return "remove";
            default: return null;
        }
    }

    private String suggestName(String perm) {
        if (!StringUtils.hasText(perm)) return "操作";
        int idx = perm.lastIndexOf(':');
        String action = idx >= 0 ? perm.substring(idx + 1) : perm;
        return action;
    }

    private Long resolveParentIdForF(Map<String, Long> menuKeyIdMap, String perm, Long defaultParentId) {
        if (menuKeyIdMap == null || menuKeyIdMap.isEmpty()) return defaultParentId;
        int idx = perm.indexOf(':');
        String prefix = idx > 0 ? perm.substring(0, idx) : perm;
        Long pid = menuKeyIdMap.get(prefix);
        if (pid != null) return pid;
        for (Map.Entry<String, Long> e : menuKeyIdMap.entrySet()) {
            if (perm.startsWith(e.getKey() + ":")) return e.getValue();
        }
        return defaultParentId;
    }

    private Map<String, Long> upsertManifestMenus(List<MenuNodeDTO> manifest) {
        if (CollectionUtils.isEmpty(manifest)) return Collections.emptyMap();
        Map<String, MenuNodeDTO> nodeMap = manifest.stream()
                .filter(n -> n != null && StringUtils.hasText(n.getKey()))
                .collect(Collectors.toMap(MenuNodeDTO::getKey, n -> n, (a, b) -> a, LinkedHashMap::new));
        Map<String, Long> keyIdMap = new HashMap<>();
        Set<String> processed = new HashSet<>();
        for (MenuNodeDTO node : manifest) {
            ensureNode(node, nodeMap, keyIdMap, processed);
        }
        Map<String, Long> cMap = new HashMap<>();
        for (MenuNodeDTO node : manifest) {
            if (StringUtils.hasText(node.getPermPrefix()) && "C".equalsIgnoreCase(node.getType())) {
                Long id = keyIdMap.get(node.getKey());
                if (id != null) {
                    cMap.put(node.getPermPrefix(), id);
                }
            }
        }
        return cMap;
    }

    private void ensureNode(MenuNodeDTO node, Map<String, MenuNodeDTO> nodeMap, Map<String, Long> keyIdMap, Set<String> processed) {
        if (node == null || processed.contains(node.getKey())) return;
        Long parentId = 0L;
        if (StringUtils.hasText(node.getParentKey())) {
            MenuNodeDTO parent = nodeMap.get(node.getParentKey());
            if (parent != null) {
                ensureNode(parent, nodeMap, keyIdMap, processed);
                parentId = keyIdMap.getOrDefault(parent.getKey(), 0L);
            }
        }
        SysMenuEntity exist = getOne(new LambdaQueryWrapper<SysMenuEntity>()
                .eq(SysMenuEntity::getParentId, parentId)
                .eq(SysMenuEntity::getType, node.getType())
                .eq(SysMenuEntity::getMenuName, node.getMenuName()));
        if (exist == null) {
            SysMenuEntity e = new SysMenuEntity();
            e.setParentId(parentId);
            e.setMenuName(node.getMenuName());
            e.setPath(node.getPath());
            e.setComponent(node.getComponent());
            e.setIcon(node.getIcon());
            e.setSort(node.getSort() == null ? 0 : node.getSort());
            e.setType(node.getType());
            save(e);
            keyIdMap.put(node.getKey(), e.getId());
        } else {
            boolean needUpdate = false;
            if (!Objects.equals(exist.getPath(), node.getPath())) {
                exist.setPath(node.getPath());
                needUpdate = true;
            }
            if (!Objects.equals(exist.getComponent(), node.getComponent())) {
                exist.setComponent(node.getComponent());
                needUpdate = true;
            }
            if (!Objects.equals(exist.getIcon(), node.getIcon())) {
                exist.setIcon(node.getIcon());
                needUpdate = true;
            }
            if (!Objects.equals(exist.getSort(), node.getSort())) {
                exist.setSort(node.getSort());
                needUpdate = true;
            }
            if (needUpdate) {
                updateById(exist);
            }
            keyIdMap.put(node.getKey(), exist.getId());
        }
        processed.add(node.getKey());
    }
}

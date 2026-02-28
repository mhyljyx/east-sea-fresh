
-- 1. 创建普通用户角色 (Role)
INSERT INTO `sys_role` (`role_name`, `role_code`, `description`, `create_time`, `update_time`, `is_del`) 
VALUES ('普通用户', 'ROLE_USER', '普通用户角色', NOW(), NOW(), '0');

-- 获取刚插入的角色ID
SET @role_id = LAST_INSERT_ID();

-- 2. 创建目的地列表菜单权限 (Menu)
-- 注意：这里假设你已经有了父菜单，如果没有，parentId 设为 0
INSERT INTO `sys_menu` (`parent_id`, `menu_name`, `path`, `component`, `perms`, `icon`, `sort`, `type`, `create_time`, `update_time`, `is_del`) 
VALUES (0, '目的地管理', '/destination', 'layout/Layout', '', 'location', 1, 'M', NOW(), NOW(), '0');

SET @parent_menu_id = LAST_INSERT_ID();

INSERT INTO `sys_menu` (`parent_id`, `menu_name`, `path`, `component`, `perms`, `icon`, `sort`, `type`, `create_time`, `update_time`, `is_del`) 
VALUES (@parent_menu_id, '目的地列表', 'list', 'destination/list', 'destination:list', 'list', 1, 'C', NOW(), NOW(), '0');

SET @menu_id = LAST_INSERT_ID();

-- 3. 关联角色与菜单 (Role-Menu)
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`, `create_time`, `update_time`, `is_del`) 
VALUES (@role_id, @menu_id, NOW(), NOW(), '0');

-- 4. 关联用户与角色 (User-Role)
-- 假设你要授权的用户ID是 9999 (根据你之前的问题描述)
-- 请确保 sys_user 表里已经有 id=9999 的用户，如果没有请先插入用户
-- INSERT INTO `sys_user` (id, account, ...) VALUES (9999, ...);

INSERT INTO `sys_user_role` (`user_id`, `role_id`, `create_time`, `update_time`, `is_del`) 
VALUES (9999, @role_id, NOW(), NOW(), '0');

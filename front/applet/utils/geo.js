/**
 * 计算地理要素集合的边界框
 * 
 * 遍历所有 Feature 的坐标，找出最小和最大的经纬度值，确定地图显示的范围。
 * 
 * @param {Array} features - GeoJSON 的 features 数组
 * @returns {Object} 包含 minLon, maxLon, minLat, maxLat 的对象
 */
const getBounds = (features) => {
  let minLon = 180, maxLon = -180, minLat = 90, maxLat = -90;
  features.forEach(feature => {
    const processCoords = (coords) => {
      // Check if it's a point [lon, lat]
      // 递归处理坐标数组，直到找到具体的经纬度点
      if (coords.length === 2 && typeof coords[0] === 'number' && typeof coords[1] === 'number') {
        minLon = Math.min(minLon, coords[0]);
        maxLon = Math.max(maxLon, coords[0]);
        minLat = Math.min(minLat, coords[1]);
        maxLat = Math.max(maxLat, coords[1]);
      } else {
        coords.forEach(processCoords);
      }
    };
    if (feature.geometry) {
      processCoords(feature.geometry.coordinates);
    }
  });
  return { minLon, maxLon, minLat, maxLat };
};

/**
 * 计算地图的缩放比例和偏移量
 * 
 * 根据 Canvas 的宽高和地理边界，计算出最合适的缩放比例，使地图能够完整显示在 Canvas 中，
 * 并自动计算偏移量以居中显示。
 * 
 * @param {Object} bounds - 地理边界对象 {minLon, maxLon, minLat, maxLat}
 * @param {number} width - Canvas 宽度
 * @param {number} height - Canvas 高度
 * @param {number} padding - 边缘内边距，默认 20
 * @returns {Object} 包含 scale (缩放比例), offsetX (X轴偏移), offsetY (Y轴偏移)
 */
const getScale = (bounds, width, height, padding = 20) => {
  const { minLon, maxLon, minLat, maxLat } = bounds;
  const lonRange = maxLon - minLon;
  const latRange = maxLat - minLat;
  const w = width - padding * 2;
  const h = height - padding * 2;
  const scaleX = w / lonRange;
  const scaleY = h / latRange;
  // 选择较小的缩放比例，确保宽和高都能容纳
  const scale = Math.min(scaleX, scaleY);
  // 计算居中偏移量
  const offsetX = (width - lonRange * scale) / 2;
  const offsetY = (height - latRange * scale) / 2;
  return { scale, offsetX, offsetY };
};

/**
 * 经纬度投影转换为屏幕坐标
 * 
 * 将地理经纬度坐标转换为 Canvas 上的像素坐标。
 * 注意：Canvas 坐标系 Y 轴向下，而地理坐标纬度向上增加，因此 Y 轴需要反转。
 * 
 * @param {number} lon - 经度
 * @param {number} lat - 纬度
 * @param {Object} bounds - 地理边界
 * @param {Object} scaleInfo - 缩放信息
 * @returns {Array} [x, y] 屏幕坐标
 */
const project = (lon, lat, bounds, scaleInfo) => {
  const { minLon, maxLat } = bounds;
  const { scale, offsetX, offsetY } = scaleInfo;
  const x = (lon - minLon) * scale + offsetX;
  // 纬度反转：(maxLat - lat)
  const y = (maxLat - lat) * scale + offsetY;
  return [x, y];
};

/**
 * 屏幕坐标反投影为经纬度
 * 
 * 将 Canvas 上的像素坐标转换回地理经纬度坐标，主要用于处理点击事件。
 * 
 * @param {number} x - 屏幕 X 坐标
 * @param {number} y - 屏幕 Y 坐标
 * @param {Object} bounds - 地理边界
 * @param {Object} scaleInfo - 缩放信息
 * @returns {Array} [lon, lat] 经纬度
 */
const unproject = (x, y, bounds, scaleInfo) => {
  const { minLon, maxLat } = bounds;
  const { scale, offsetX, offsetY } = scaleInfo;
  const lon = (x - offsetX) / scale + minLon;
  const lat = maxLat - (y - offsetY) / scale;
  return [lon, lat];
};

/**
 * 在 Canvas 上绘制地理要素
 * 
 * 支持 Polygon 和 MultiPolygon 类型的几何图形绘制。
 * 
 * @param {Object} ctx - Canvas 绘图上下文
 * @param {Array} features - 要绘制的 features 数组
 * @param {Object} bounds - 地理边界
 * @param {Object} scaleInfo - 缩放信息
 */
const drawFeatures = (ctx, features, bounds, scaleInfo) => {
    features.forEach(feature => {
        const geometry = feature.geometry;
        ctx.beginPath();
        
        // 绘制单个环（Ring）
        const drawRing = (ring) => {
             ring.forEach((pt, i) => {
                 const [x, y] = project(pt[0], pt[1], bounds, scaleInfo);
                 if (i === 0) ctx.moveTo(x, y);
                 else ctx.lineTo(x, y);
             });
             ctx.closePath();
        };

        if (geometry.type === 'Polygon') {
             // 绘制外环
             drawRing(geometry.coordinates[0]);
             // 绘制内环（孔洞）
             // Canvas 的 'nonzero' 或 'evenodd' 填充规则通常能正确处理同路径下的孔洞
             for(let k=1; k<geometry.coordinates.length; k++) {
                 drawRing(geometry.coordinates[k]);
             }
        } else if (geometry.type === 'MultiPolygon') {
             // 遍历多个 Polygon
             geometry.coordinates.forEach(poly => {
                 drawRing(poly[0]);
                 for(let k=1; k<poly.length; k++) {
                     drawRing(poly[k]);
                 }
             });
        }
        
        // 样式应在调用前设置好，这里执行填充和描边
        ctx.fill();
        ctx.stroke();
    });
};

/**
 * 判断点是否在多边形内
 * 
 * 使用射线法（Ray-Casting Algorithm）判断。
 * 
 * @param {Array} point - [x, y] 测试点坐标
 * @param {Array} vs - 多边形顶点数组 [[x1, y1], [x2, y2], ...]
 * @returns {boolean} 是否在多边形内
 */
const isPointInPolygon = (point, vs) => {
  const x = point[0], y = point[1];
  let inside = false;
  for (let i = 0, j = vs.length - 1; i < vs.length; j = i++) {
    const xi = vs[i][0], yi = vs[i][1];
    const xj = vs[j][0], yj = vs[j][1];
    
    // 射线与多边形边的交点判断
    const intersect = ((yi > y) !== (yj > y)) &&
      (x < (xj - xi) * (y - yi) / ((yj - yi) || 0.000001) + xi);
    if (intersect) inside = !inside;
  }
  return inside;
};

/**
 * 查找指定屏幕坐标下的地理要素
 * 
 * 将屏幕坐标转换为地理坐标，然后遍历所有 feature，检查该点是否位于 feature 的几何形状内。
 * 
 * @param {number} x - 屏幕 X 坐标
 * @param {number} y - 屏幕 Y 坐标
 * @param {Array} features - 地理要素列表
 * @param {Object} bounds - 地理边界
 * @param {Object} scaleInfo - 缩放信息
 * @returns {Object|null} 找到的 feature 对象，未找到返回 null
 */
const findFeatureAtPoint = (x, y, features, bounds, scaleInfo) => {
    // 1. 将屏幕点击坐标反投影回经纬度坐标
    const [lon, lat] = unproject(x, y, bounds, scaleInfo);
    const point = [lon, lat];

    for (const feature of features) {
        const geometry = feature.geometry;
        
        // 辅助函数：检查点是否在 Polygon 中
        const checkPolygon = (rings) => {
             // 简化处理：只检查外环（第一个 Ring），忽略内环（孔洞）
             // 对于点击命中测试，这种简化通常是可以接受的
             if (rings.length > 0) {
                 return isPointInPolygon(point, rings[0]);
             }
             return false;
        };

        if (geometry.type === 'Polygon') {
            if (checkPolygon(geometry.coordinates)) return feature;
        } else if (geometry.type === 'MultiPolygon') {
            // MultiPolygon 包含多个 Polygon，只要点在任意一个 Polygon 内即可
            for (const poly of geometry.coordinates) {
                 if (checkPolygon(poly)) return feature;
            }
        }
    }
    return null;
};

module.exports = {
  getBounds,
  getScale,
  project,
  drawFeatures,
  isPointInPolygon,
  findFeatureAtPoint
};

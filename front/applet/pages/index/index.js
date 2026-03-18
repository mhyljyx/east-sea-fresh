const geo = require('../../utils/geo.js')
const loadGeo = require('../../utils/geo-data.js')

Page({
  data: {
    width: 0,
    height: 0
  },
  
  /**
   * 页面加载时的生命周期函数
   * 
   * 初始化 Canvas，获取设备信息并设置 Canvas 尺寸（处理高分屏 DPR）。
   * 读取 GeoJSON 数据并进行初步的边界和缩放计算，最后绘制地图。
   */
  onLoad() {
    const q = wx.createSelectorQuery()
    q.select('#mapCanvas').node().exec(res => {
      if (!res[0]) return
      const canvas = res[0].node
      const ctx = canvas.getContext('2d')
      const { windowWidth, windowHeight } = wx.getSystemInfoSync()
      const dpr = wx.getSystemInfoSync().pixelRatio
      
      // 设置 Canvas 物理像素尺寸，解决高清屏模糊问题
      canvas.width = windowWidth * dpr
      canvas.height = windowHeight * dpr
      ctx.scale(dpr, dpr)
      
      this.setData({ width: windowWidth, height: windowHeight })
      
      try {
        // 直接使用 require 引入的 GeoJSON 数据，避免 fs 读取权限问题
        loadGeo.loadGeoJson('http://111.229.109.185:9100/api/v1/download-shared-object/aHR0cDovLzEyNy4wLjAuMTo5MDAwL2dlby1kYXRhLyVFNiVCNSU5OSVFNiVCMSU5RiVFNyU5QyU4MS5nZW9qc29uP1gtQW16LUFsZ29yaXRobT1BV1M0LUhNQUMtU0hBMjU2JlgtQW16LUNyZWRlbnRpYWw9VEFMV0hTQ0gzVzRDTktTNlgyMkQlMkYyMDI2MDMxOCUyRnVzLWVhc3QtMSUyRnMzJTJGYXdzNF9yZXF1ZXN0JlgtQW16LURhdGU9MjAyNjAzMThUMDg1NTI1WiZYLUFtei1FeHBpcmVzPTQzMjAwJlgtQW16LVNlY3VyaXR5LVRva2VuPWV5SmhiR2NpT2lKSVV6VXhNaUlzSW5SNWNDSTZJa3BYVkNKOS5leUpoWTJObGMzTkxaWGtpT2lKVVFVeFhTRk5EU0ROWE5FTk9TMU0yV0RJeVJDSXNJbVY0Y0NJNk1UYzNNemcyTnpNd01Td2ljR0Z5Wlc1MElqb2lZV1J0YVc0aWZRLkV5azd4VFNfZk9Kcm5wWlBxUndJRHBwSkxFRkdiLXlxQnp3V2JUM2IxRTVMMDVTUXhBcjg4RUxuenZPbWhqd3VOU3AxU0pEYlp5YkNtT0dYalhPYk5nJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCZ2ZXJzaW9uSWQ9bnVsbCZYLUFtei1TaWduYXR1cmU9MmZjNjQxYTExZDllNWI1MjliZjVkYWFlMGZjZjJjOTZlM2I0ZTUwMDI3ZDRhNzhkMDc5NjZiNjMxODgyYThiZQ')
        .then(geoJson => {
          this.features = geoJson.features;
          
          // 计算整个地图的边界框
          this.bounds = geo.getBounds(this.features)
          // 根据窗口大小计算缩放比例，预留 20px 边距
          this.scaleInfo = geo.getScale(this.bounds, windowWidth, windowHeight, 20)
          
          // 绘制地图
          this.drawMap(ctx)
        })
        
        // 保存上下文和 Canvas 引用以便后续使用
        this.ctx = ctx
        this.canvas = canvas
      } catch (e) {
        console.error('Failed to load geojson', e)
        wx.showToast({ title: '加载地图失败', icon: 'none' })
      }
    })
  },
  
  /**
   * 绘制地图
   * 
   * 遍历 GeoJSON features，绘制每个城市的多边形区域和名称标签。
   * 
   * @param {Object} ctx - Canvas 绘图上下文
   */
  drawMap(ctx) {
    if (!this.features) return
    
    ctx.clearRect(0, 0, this.data.width, this.data.height)

    // 如果当前有选中的城市，且不处于触摸状态（松手后），则只渲染该城市
    // 这里我们用 currentCity 来判断是否处于下钻状态
    if (this.data.currentCity) {
        // 绘制半透明蒙层，弱化全省地图
        ctx.save()
        ctx.globalAlpha = 0.3
        this.drawProvinceMap(ctx, false) // 绘制底层全省地图，不处理高亮
        ctx.restore()

        // 绘制选中的城市（区县详情）
        this.drawCountyMap(ctx)
    } else {
        // 正常绘制全省地图
        this.drawProvinceMap(ctx, true)
    }
  },

  // 抽离全省地图绘制逻辑
  drawProvinceMap(ctx, handleActive) {
    // 定义一组卡通风格的颜色（高饱和度/糖果色）
    const colors = [
      { top: '#FF9C6E', side: '#D46B40' }, // 珊瑚橙
      { top: '#FFC069', side: '#D49645' }, // 杏黄
      { top: '#FFF566', side: '#D4C933' }, // 柠檬黄
      { top: '#95DE64', side: '#5FB333' }, // 清新绿
      { top: '#5CDBD3', side: '#2BA69E' }, // 蒂芙尼蓝
      { top: '#69C0FF', side: '#338ACC' }, // 天空蓝
      { top: '#85A5FF', side: '#506ECC' }, // 矢车菊蓝
      { top: '#B37FEB', side: '#804AB8' }, // 紫罗兰
      { top: '#FF85C0', side: '#CC4D88' }  // 樱花粉
    ]
    
    // 1. 绘制立体侧面（阴影层）
    const depth = 6; 
    
    for (let d = depth; d >= 1; d--) {
      ctx.save()
      ctx.translate(0, d)
      
      this.features.forEach((feature, index) => {
        const colorSet = colors[index % colors.length]
        ctx.fillStyle = colorSet.side 
        ctx.strokeStyle = colorSet.side 
        ctx.lineWidth = 0.5
        geo.drawFeatures(ctx, [feature], this.bounds, this.scaleInfo)
      })
      
      ctx.restore()
    }
    
    // 2. 绘制顶层平面
    this.features.forEach((feature, index) => {
      const isActive = handleActive && this.data.activeFeature && this.data.activeFeature.properties.name === feature.properties.name
      const colorSet = colors[index % colors.length]
      
      ctx.save()
      
      // 激活状态：向上偏移，产生悬浮效果
      if (isActive) {
          ctx.translate(0, -5) // 向上浮动
          ctx.shadowColor = 'rgba(0, 0, 0, 0.5)'
          ctx.shadowBlur = 10
          ctx.shadowOffsetY = 10
      }

      ctx.fillStyle = colorSet.top
      ctx.strokeStyle = '#FFFFFF' 
      ctx.lineWidth = isActive ? 3 : 2 
      
      geo.drawFeatures(ctx, [feature], this.bounds, this.scaleInfo)
      
      // 3. 绘制城市名称标签
      const props = feature.properties
      if (props.name && (props.centroid || props.center)) {
        const center = props.centroid || props.center
        const [x, y] = geo.project(center[0], center[1], this.bounds, this.scaleInfo)
        
        ctx.font = isActive ? 'bold 14px sans-serif' : 'bold 12px sans-serif' 
        ctx.textAlign = 'center'
        ctx.textBaseline = 'middle'
        
        ctx.strokeStyle = 'rgba(255, 255, 255, 0.8)'
        ctx.lineWidth = 3
        ctx.strokeText(props.name, x, y)
        
        ctx.fillStyle = '#595959' 
        ctx.fillText(props.name, x, y)
      }
      
      ctx.restore()
    })
  },

  // 绘制区县地图
  drawCountyMap(ctx) {
      if (!this.countyFeatures) return

      const colors = ['#FFE4B5', '#FFDAB9', '#E6E6FA', '#D8BFD8', '#B0C4DE', '#ADD8E6']
      
      // 使用全省地图的 bounds 和 scaleInfo 来保持位置一致，或者重新计算以放大显示？
      // 需求描述是“悬浮在大地图上”，通常意味着局部放大或者原位显示。
      // 为了让用户看清“底部的大地图”，我们保持原位显示，但在视觉上让它浮起来。
      // 如果要重新渲染出区县详情，通常需要放大一点，或者就在原位画得更细致。
      // 这里我们在原位绘制区县，并加上阴影让它看起来悬浮在半透明的全省地图之上。

      ctx.save()
      // 可以稍微放大一点点或者加一个整体的阴影
      ctx.shadowColor = 'rgba(0, 0, 0, 0.5)'
      ctx.shadowBlur = 20
      ctx.shadowOffsetY = 10
      
      this.countyFeatures.forEach((feature, index) => {
          ctx.fillStyle = colors[index % colors.length]
          ctx.strokeStyle = '#FFFFFF'
          ctx.lineWidth = 1
          
          geo.drawFeatures(ctx, [feature], this.bounds, this.scaleInfo)
          
          const props = feature.properties
          if (props.name && (props.centroid || props.center)) {
             const center = props.centroid || props.center
             const [x, y] = geo.project(center[0], center[1], this.bounds, this.scaleInfo)
             
             ctx.fillStyle = '#333333'
             ctx.font = '10px sans-serif'
             ctx.textAlign = 'center'
             ctx.textBaseline = 'middle'
             ctx.fillText(props.name, x, y)
          }
      })
      ctx.restore()
  },

  onTouchStart(e) {
    if (this.data.currentCity) return // 如果已经下钻，暂时禁用触摸或者实现区县点击
    if (!this.features) return
    const x = e.touches[0].x
    const y = e.touches[0].y
    this.handleTouch(x, y)
  },

  onTouchMove(e) {
    if (this.data.currentCity) return
    if (!this.features) return
    const x = e.touches[0].x
    const y = e.touches[0].y
    this.handleTouch(x, y)
  },

  onTouchEnd(e) {
    if (this.data.currentCity) return

    // 松手时，如果有选中区域，则显示详情
    if (this.data.activeFeature) {
       const cityName = this.data.activeFeature.properties.name
       this.showCityDetail(cityName)
    }
    
    this.setData({ activeFeature: null })
    // 不需要单独调用 drawMap，showCityDetail 会调用
  },

  showCityDetail(cityName) {
    try {
      if (loadGeo[cityName]) {
           const cityGeoJson = loadGeo[cityName]
           this.countyFeatures = cityGeoJson.features // 保存区县数据
           this.setData({
             currentCity: cityName
           })
           // 重新绘制，此时会进入 drawMap 的下钻逻辑
           this.drawMap(this.ctx)
      } else {
           console.warn('City data not found:', cityName)
           this.setData({ activeFeature: null })
           this.drawMap(this.ctx)
      }
    } catch (e) {
      console.error(e)
      wx.showToast({ title: '暂无数据', icon: 'none' })
    }
  },

  resetMap() {
      this.setData({ currentCity: null })
      this.countyFeatures = null
      this.drawMap(this.ctx)
  },

  handleTouch(x, y) {
    const feature = geo.findFeatureAtPoint(x, y, this.features, this.bounds, this.scaleInfo)
    if (feature) {
      if (this.data.activeFeature && this.data.activeFeature.properties.name === feature.properties.name) {
        return // 相同区域不重绘
      }
      this.setData({ activeFeature: feature })
      this.drawMap(this.ctx)
    } else {
      if (this.data.activeFeature) {
        this.setData({ activeFeature: null })
        this.drawMap(this.ctx)
      }
    }
  },
})

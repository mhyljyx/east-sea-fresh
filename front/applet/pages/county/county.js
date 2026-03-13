const geo = require('../../utils/geo.js')
const geoData = require('../../utils/geo-data.js')

Page({
  data: {
    city: '',
    counties: [],
    width: 0,
    height: 0
  },
  
  /**
   * 页面加载时的生命周期函数
   * 
   * 1. 获取 URL 参数中的城市名称。
   * 2. 初始化 Canvas，设置尺寸。
   * 3. 根据城市名称动态加载对应的 GeoJSON 文件。
   * 4. 解析 GeoJSON，提取区县列表，计算边界并绘制地图。
   * 
   * @param {Object} options - 页面启动参数，包含 city
   */
  onLoad(options) {
    const city = decodeURIComponent(options.city || '')
    this.setData({ city })
    
    const q = wx.createSelectorQuery()
    q.select('#countyCanvas').node().exec(res => {
      if (!res[0]) return
      const canvas = res[0].node
      const ctx = canvas.getContext('2d')
      const { windowWidth, windowHeight } = wx.getSystemInfoSync()
      const dpr = wx.getSystemInfoSync().pixelRatio
      
      // 设置 Canvas 高度为屏幕高度的 50%（与 WXSS 中的 50vh 保持一致）
      const canvasHeight = Math.floor(windowHeight * 0.5)
      
      // 处理高清屏 DPR
      canvas.width = windowWidth * dpr
      canvas.height = canvasHeight * dpr
      ctx.scale(dpr, dpr)
      
      this.setData({ width: windowWidth, height: canvasHeight })
      
      try {
        // 使用预加载的 GeoJSON 数据
        const geoJson = geoData[city]
        
        if (!geoJson) {
           throw new Error('City geojson not found: ' + city)
        }
        
        this.features = geoJson.features
        
        // 提取区县名称，用于页面下方的列表展示
        const counties = this.features.map(f => f.properties.name).filter(n => n)
        this.setData({ counties })
        
        // 计算边界和缩放
        this.bounds = geo.getBounds(this.features)
        this.scaleInfo = geo.getScale(this.bounds, windowWidth, canvasHeight, 20)
        
        // 绘制地图
        this.drawMap(ctx)
        
        this.ctx = ctx
        this.canvas = canvas
      } catch (e) {
        console.error('Failed to load geojson for city', city, e)
        wx.showToast({ title: '加载地图失败: ' + city, icon: 'none' })
      }
    })
  },
  
  /**
   * 绘制区县地图
   * 
   * 遍历该市的所有区县 Feature，绘制多边形和标签。
   * 使用一组柔和的色调来区分不同区县。
   * 
   * @param {Object} ctx - Canvas 绘图上下文
   */
  drawMap(ctx) {
      if (!this.features) return
      
      ctx.clearRect(0, 0, this.data.width, this.data.height)
      
      // 定义一组用于区县地图的配色方案（柔和色调）
      const colors = ['#FFE4B5', '#FFDAB9', '#E6E6FA', '#D8BFD8', '#B0C4DE', '#ADD8E6']
      
      this.features.forEach((feature, index) => {
          // 循环设置填充颜色
          ctx.fillStyle = colors[index % colors.length]
          ctx.strokeStyle = '#FFFFFF'
          ctx.lineWidth = 1
          
          // 绘制多边形
          geo.drawFeatures(ctx, [feature], this.bounds, this.scaleInfo)
          
          // 绘制区县名称标签
          const props = feature.properties
          if (props.name && (props.centroid || props.center)) {
             const center = props.centroid || props.center
             const [x, y] = geo.project(center[0], center[1], this.bounds, this.scaleInfo)
             
             ctx.fillStyle = '#555555'
             ctx.font = '10px sans-serif'
             ctx.textAlign = 'center'
             ctx.textBaseline = 'middle'
             ctx.fillText(props.name, x, y)
          }
      })
  }
})

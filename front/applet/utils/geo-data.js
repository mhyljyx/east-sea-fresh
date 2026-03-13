function loadGeoJson(url) {
  return new Promise((resolve, reject) => {
    wx.request({
      url: url,
      method: 'GET',
      success(res) {
        resolve(res.data)
      },
      fail(err) {
        reject(err)
      }
    })
  })
}

module.exports = {
  loadGeoJson
}
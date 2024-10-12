/**
 * 通过url下载文件并对下载的文件更名
 * @param {*} url
 * @param {*} filename
 */
export const downloadFile = (url, filename) => {
  function getBlob(url) {
    return new Promise((resolve) => {
      const XML = new XMLHttpRequest()
      XML.open('GET', url, true)
      XML.responseType = 'blob'
      XML.onload = () => {
        if (XML.status === 200) {
          resolve(XML.response)
        }
      }
      XML.send()
    })
  }
  //下载文件
  function saveAs(blob, filename) {
    const elelink = document.createElement('a')
    elelink.style.display = 'none'
    elelink.download = filename
    elelink.href = window.URL.createObjectURL(blob)
    document.body.appendChild(elelink)
    elelink.click()
    document.body.removeChild(elelink)
  }
  // 调用以上方法进行下载
  getBlob(url).then((blob) => {
    saveAs(blob, filename)
  })
}

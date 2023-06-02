/**
 * 根据给定的路径 dir，生成面包屑导航，并存储在 breadcrumb 数组中。
 * 如果路径 dir 的长度不超过 showLength，则直接将所有路径都添加到面包屑中；
 * 否则只添加最后 showLength 个路径，并在开头添加 ... 表示省略了一部分路径
 * @param {String} dir 目录
 * @param {Number} showLength 显示的长度
 * @returns breadcrumb 数组
 */
export function generateBreadcrumb(dir, showLength) {
  // 初始化面包屑数组
  const breadcrumb = []
  // 将路径按照 / 分割成数组，并过滤掉空字符串
  const dirArr = dir.split('/').filter(str => str != '')
   // 获取路径数组的长度
  const dirLength = dirArr.length

  let path = ''
  if (dirLength <= showLength) {
    // 如果路径数组长度不超过最大显示路径数量
    for (let i = 0; i < dirLength; i++) {
      if (dirArr[i] !== '') {
        const obj = {
          name: dirArr[i],
          path: `${path}/${dirArr[i]}`
        }
        // 将面包屑对象添加到面包屑数组中
        breadcrumb.push(obj)
        // 更新路径变量
        path = obj.path
      }
    }
  } else {
    // 如果路径数组长度超过最大显示路径数量
    // 在开头添加省略符
    breadcrumb.push({ name: '...', path: 'null' })
    // 获取省略路径的前缀
    const prefixPath = `/${dirArr.slice(0, dirLength - showLength).join('/')}`
    // 遍历最后几个路径
    for (let i = dirLength - showLength; i < dirLength; i++) {
       // 更新路径变量
      path += `/${dirArr[i]}`
      // 创建面包屑对象
      breadcrumb.push({ name: dirArr[i], path: `${prefixPath}${path}` })
    }
  }

  return breadcrumb;
}
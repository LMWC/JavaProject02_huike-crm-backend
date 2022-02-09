/**
 * @param {string} path
 * @returns {Boolean}
 */
function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
function validUsername(str) {
  const valid_map = ['admin', 'editor']
  return valid_map.indexOf(str.trim()) >= 0
}

/**
 * @param {string} url
 * @returns {Boolean}
 */
function validURL(url) {
  const reg = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/
  return reg.test(url)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
function validLowerCase(str) {
  const reg = /^[a-z]+$/
  return reg.test(str)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
function validUpperCase(str) {
  const reg = /^[A-Z]+$/
  return reg.test(str)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
function validAlphabets(str) {
  const reg = /^[A-Za-z]+$/
  return reg.test(str)
}

/**
 * @param {string} email
 * @returns {Boolean}
 */
function validEmail(email) {
  const reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  return reg.test(email)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
function isString(str) {
  if (typeof str === 'string' || str instanceof String) {
    return true
  }
  return false
}

/**
 * @param {Array} arg
 * @returns {Boolean}
 */
function isArray(arg) {
  if (typeof Array.isArray === 'undefined') {
    return Object.prototype.toString.call(arg) === '[object Array]'
  }
  return Array.isArray(arg)
}

/* 验证是否带有特殊字符 */
function isVerification (rule, value, callback) {
  let regEn = /[`~!@#$%^&*()_+<>?:"{},./;'[\]]/im
  let regCn = /[·！#￥（——）：；“”‘、，|《。》？、【】[\]]/im
  if (regCn.test(value) || regEn.test(value)) {
    callback(new Error('禁止输入特殊字符'))
  } else {
    callback()
  }
  return true
}

/* 验证手机号 */
function isValidatePhone (rule, value, callback) {
  if (!/^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(value)) {
    callback(new Error('请输入正确的手机号'))
  } else {
    callback()
  }
  return true
}

/* 验证QQ */
function isValidateQq (rule, value, callback) {
  if(value){
    let reg = /^[1-9][0-9]{4,9}$/gim;
    if (!reg.test(value)) {
      callback(new Error('请输入正确的QQ号'))
    } else {
      callback()
    }
  }else{
    callback()
  }
  return true
}

/* 验证微信 */
function isValidateWx (rule, value, callback) {
  if(value){
    let reg = /^[a-zA-Z]([-_a-zA-Z0-9]{5,19})+$/;
    if (!reg.test(value)) {
      callback(new Error('请输入正确的微信号'))
    } else {
      callback()
    }
  }else{
    callback()
  }
  return true
}

/* 验证姓名 */
function isValidateCnName(rule, value, callback){
  if(value){
    let reg = /^[\u4e00-\u9fa5]{2,4}$/;
    if (!reg.test(value)) {
      callback(new Error('请输入正确的姓名'))
    } else {
      callback()
    }
  }else{
    callback()
  }
  return true
}

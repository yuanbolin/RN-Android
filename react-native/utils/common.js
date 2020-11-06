import { NativeModules, Platform, Dimensions } from 'react-native'
import ExtraDimensions from 'react-native-extra-dimensions-android'
const { StatusBarManager } = NativeModules
//iPhone11大小
let { width, height } = Dimensions.get('window')
const x11_width = 414
const x11_height = 896
// iPhoneX
const X_width = 375
const X_height = 812
const isIPhoneX =
  Platform.OS === 'ios' &&
  ((height === X_height && width === X_width) ||
    (height === X_width && width === X_height) ||
    (height === x11_width && width === x11_height) ||
    (width === x11_width && height === x11_height))
// iphoneX 顶部留白的兼容处理
export function isIPhoneXPaddTop(number) {
  number = isNaN(+number) ? 0 : +number
  return number + (isIPhoneX ? 44 : 20)
}
//iPhoneX 底部高度兼容处理
export function isIPhoneXFooter(number) {
  number = isNaN(+number) ? 0 : +number
  return number + (isIPhoneX ? 34 : 0)
}
//这里如果isIPhoneX===1，则为iPhoneX,否则不是
const STATUSBAR_HEIGHT = Platform.OS === 'ios' ? (isIPhoneX ? 44 : 20) : StatusBarManager.HEIGHT
const os = Platform.OS
const _width = os === 'android' ? width : width
let _height =
  os === 'android' ? ExtraDimensions.getRealWindowHeight() - ExtraDimensions.getStatusBarHeight() - ExtraDimensions.getSmartBarHeight() - 45 : height * 0.96
const Constansts = {
  username: '', //用户名
  isLogin: 0, //0：未登录 1：已登录
  SERVER_SOCKET: 'wss://xxx',
  STATUSBAR_HEIGHT: STATUSBAR_HEIGHT,
  // SERVER_NAME: 'https://wangkangning.utools.club',
  // SERVER_NAME: 'http://www.ruoweiedu.com:5555',
  SERVER_NAME: NativeModules.AndroidInfo.BASEURL,
  token: '',
  imgfileSize: 2048576, //2mb
  videofileSize: 20971520, //20mb
  width: _width,
  height:_height,
  os: os
  //...后续待加
}

function keyGenerate() {
  var s = []
  var hexDigits = '0123456789abcdef'
  for (var i = 0; i < 36; i++) {
    s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1)
  }
  s[14] = '4' // bits 12-15 of the time_hi_and_version field to 0010
  s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1) // bits 6-7 of the clock_seq_hi_and_reserved to 01
  s[8] = s[13] = s[18] = s[23] = '-'

  var uuid = s.join('')
  return uuid
}

export { Constansts, keyGenerate }

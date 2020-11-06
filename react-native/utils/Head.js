/*入口路由文件*/
import * as React from 'react'
import { Text, View, StyleSheet, Image, TouchableOpacity,NativeModules } from 'react-native'
import { DatePicker, Icon } from '@ant-design/react-native'
import LinearGradient from 'react-native-linear-gradient'

import { Constansts } from './common'
import moment from 'moment/moment'
let { width, height, STATUSBAR_HEIGHT } = Constansts

export default function Header({ scene, previous, navigation }) {
  const { options } = scene.descriptor
  const title = options.headerTitle !== undefined ? options.headerTitle : options.title !== undefined ? options.title : scene.route.name

  return (
    <LinearGradient start={{ x: 0, y: 0 }} end={{ x: 1, y: 0 }} colors={['#608CE8', '#193EAF']} style={[styles.flexRow, styles.header, options.headerStyle]}>
      <Text style={[styles.headerTxt, options.textStyle]}>{title}</Text>
      {previous && (
        <TouchableOpacity onPress={() => navigation.goBack()}>
          <Image source={require('../images/icon/back.png')} style={styles.leftBack} />
        </TouchableOpacity>
      )}
      {!previous && (
          <TouchableOpacity onPress={() => NativeModules.AndroidInfo.finishReact()}>
            <Image source={require('../images/icon/back.png')} style={styles.leftBack} />
          </TouchableOpacity>
      )}
      {options.right && options.type == 'month' && (
        <DatePicker value={options.value} mode='month' defaultDate={new Date()} onChange={value => {
         options.handle(moment(value).format('YYYY'),moment(value).format('MM'))
          navigation.setOptions({value:value,rightTxt:moment(value).format('YYYY年MM月')})
        }}>
          <TouchableOpacity>
            <View style={[styles.flexRow, { marginRight: 20, paddingTop: 5 }]}>
              <Text style={styles.right}>{options.rightTxt}</Text>
              <Icon name={'down'} color={'#fff'} size={'xs'} />
            </View>
          </TouchableOpacity>
        </DatePicker>
      )}
      {options.right && options.type == 'add' && (
        <TouchableOpacity onPress={() => options.handle()} style={[styles.flexRow, { marginRight: 20, paddingTop: 5 }]}>
          <Icon name={'plus-circle'} color={'#fff'} size={'md'} />
        </TouchableOpacity>
      )}
    </LinearGradient>
  )
}
const styles = StyleSheet.create({
  header: {
    backgroundColor: 'blue',
    height: 45,
    justifyContent: 'space-between'
  },
  headerTxt: {
    position: 'absolute',
    left: 0,
    top: 0,
    fontSize: 18,
    color: '#fff',
    textAlign: 'center',
    fontWeight: 'bold',
    width: width,
    lineHeight: 45
  },
  leftBack: {
    marginLeft: 20,
    width: 12,
    height: 20
  },
  right: {
    color: '#fff',
    lineHeight: 45
  },
  flexRow: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'flex-start'
  }
})

import React from 'react'
import { NativeModules, Text, View, ScrollView, StyleSheet, BackHandler, AppState, Linking } from 'react-native'
import { Portal, Toast } from '@ant-design/react-native'
import LinearGradient from 'react-native-linear-gradient'

import { Constansts } from '../utils/common'
import { get, post } from '../utils/http'
import { navigationRef } from '../utils/RootNavigation'

const width = Constansts.width
const height = Constansts.height

export default class Jixiaokaohe extends React.Component {
  constructor(props) {
    super(props)
    this.state = {}
  }

  componentDidMount() {
    //    console.log(NativeModules.AndroidInfo.getIntentData)
    NativeModules.AndroidInfo.getIntentData().then(res => {
      console.log('==>', res)
    })
    if (AppState.currentState === 'active') {
      Linking.getInitialURL().then(url => {
        console.warn(`AppState.currentState Url:${url}`)
      })
    }
    AppState.addEventListener('change', this.onAppStateChange)
    // this.props.navigation.setOptions({ handle: this.onChange })
    // NativeModules.AndroidInfo.hideBar()
    // NativeModules.AndroidInfo.closeAndroidBack()
    // this._unsubscribe = this.props.navigation.addListener('focus', () => {
    //   // do something
    //   this.getqingxiaojialist()
    // })
    // BackHandler.addEventListener('hardwareBackPress', this.onBackAndroid)
  }

  componentWillUnmount() {
    // this._unsubscribe()
    // BackHandler.removeEventListener('hardwareBackPress', this.onBackAndroid)
  }

  onAppStateChange = nextAppState => {
    if (nextAppState === 'active') {
      Linking.getInitialURL().then(url => {
        console.warn(`onAppStateChange Url:${url}`)
      })
    }
  }

  onBackAndroid = () => {
    this.props.navigation.goBack()
    return true
  }

  render() {
    return (
      <View style={styles.container}>
        <Text>你成功跳转到另一个页面!</Text>
      </View>
    )
  }
}
const styles = StyleSheet.create({
  container: {
    flex: 1,
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    fontWeight: 'bold'
  }
})

import React from 'react'
import { NativeModules, Text, View, ScrollView, StyleSheet, BackHandler } from 'react-native'
import { Portal, Toast } from '@ant-design/react-native'
import LinearGradient from 'react-native-linear-gradient'

import { Constansts } from '../utils/common'
import { get, post } from '../utils/http'

let width = Constansts.width
let height = Constansts.height

export default class Jixiaokaohe extends React.Component {
  constructor(props) {
    super(props)
    this.state = {}
  }

  componentDidMount() {
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

  onBackAndroid = () => {
    this.props.navigation.goBack()
    return true
  }

  render() {
    return (
      <View style={styles.container}>
        <Text>Hello World!</Text>
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

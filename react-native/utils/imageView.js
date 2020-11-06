import React, { Component } from 'react';
import {
  View,
  Text,
  Platform,
  ActivityIndicator, NativeModules,
} from 'react-native';

// var RNFS = require('react-native-fs');
// import CameraRoll from "@react-native-community/cameraroll";
import ImageViewer from 'react-native-image-zoom-viewer';
import { Provider, Toast, Button, Portal ,Icon} from "@ant-design/react-native";

import { Constansts ,keyGenerate} from "./common";
let width = Constansts.width
let height = Constansts.height
class SaveImage extends Component {

  constructor(props) {
    super(props);
    this.state = {
      imgindex: 0
    };
  }

  componentDidMount() {
    this.setState({
      imgindex: this.props.route.params.imgindex
    });
  }


  renderLoad() {
    //这里是写的一个loading
    return (
      <View style={{ marginTop: height / 2 - 20 }}>
        <ActivityIndicator animating={true} size={"large"} />
      </View>
    );
  }

  savePhoto = async () => {
    //保存图片
    let { imgindex } = this.state;
    NativeModules.AndroidInfo.showLoading()
    if (Platform.OS === 'android') {
      let url =
        Constansts.SERVER_NAME +
        this.props.navigation.route.params.imgList[imgindex];
      const storeLocation = `${RNFS.DocumentDirectoryPath}`;
      let pathName = new Date().getTime() + `file${keyGenerate()}.png`;
      let downloadDest = `${storeLocation}/${pathName}`;
      const ret = await RNFS.downloadFile({
        fromUrl: url,
        toFile: downloadDest,
      });
      ret.promise.then(res => {
        if (res && res.statusCode === 200) {
          var promise = CameraRoll.saveToCameraRoll("file://" + downloadDest);
          promise
            .then(function(result) {
              alert("图片已保存至相册");
              NativeModules.AndroidInfo.closeLoading()
            })
            .catch(function(error) {
              alert("保存失败");
              NativeModules.AndroidInfo.closeLoading()
            });
        }
      });
    } else {
      let url =
        Constansts.SERVER_NAME +
        this.props.navigation.route.params.imgList[imgindex];
      let promise = CameraRoll.saveToCameraRoll(url);
      promise
        .then(function(result) {
          alert("已保存到系统相册");
          Portal.remove(key2);
        })
        .catch(function(error) {
          alert('保存失败！\n' + error);
          NativeModules.AndroidInfo.closeLoading()
        });
    }
  };
  //返回上一级页面
  huiqu() {
    this.setState({ on: false });
    this.props.navigation.goBack(null);
  }

  changeimg = i => {
    //滑动切换图片
    this.setState({
      imgindex: i
    });
  };

  render() {
    let { imgList } = this.props.route.params;
    let images = [];
    for (let i = 0; i < imgList.length; i++) {
      const element = imgList[i];
      images.push({ url: element });
    }
    return (
      <Provider>
        <Text
          onPress={() => {
            this.huiqu();
          }}
          style={{
            position: 'absolute',
            zIndex: 9999,
            top: Constansts.STATUSBAR_HEIGHT + 10,
            right: 8,
            backgroundColor: 'rgba(0,0,0,0.5)',
            paddingHorizontal: 8,
          }}
        >
          <Icon name="close" size={'lg'} color="#ccc" />
        </Text>
        <ImageViewer
          index={this.state.imgindex} // 默认选中第几张图
          imageUrls={images} // 照片路径
          enableImageZoom={true} // 是否开启手势缩放
          saveToLocalByLongPress={true} //是否开启长按保存
          failImageSource={{url:'https://xunte.vip/static/common/images/20200622102725.jpg'}} // 加载失败图片
          loadingRender={this.renderLoad}
          enableSwipeDown={false}
          // menuContext={{ saveToLocal: '保存图片', cancel: "取消" }}
          // onChange={index => this.changeimg(index)} // 图片切换时触发
          // onClick={() => { // 图片单击事件
          //     this._Close()
          // }}
          // onSave={url => {
          //   this.savePhoto(url);
          // }}
          style={{ flex: 1 }}
        />
      </Provider>
    );
  }
}

export default SaveImage;

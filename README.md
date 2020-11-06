
# 分支管理
## base_android 分支
### 分支说明
- 该分支为安卓RN混合的空框架
- 该分支是安卓为主题混合让你的项目
- 用于需求只有安卓端开发
- 用于尝试安卓混合使用

### 重大更新历史
- 2020/08/13 -创建分支，分支命名为base_android

### 需实现目标
- 适应公司团队人员分配，让更多前端同学参与移动端开发
- 使用RN可复用安卓和IOS两端代码
- 减少学习成本

### 场景实例
场景一、安卓页面嵌套RN页面

- 举例：智管平台绩效考核模块，核心是一些表格页面，和上传图片
- 人员知识储备：能够熟练开发android页面，掌握集成RN开发环境和与RN的通信方法；
               熟练掌握RN的特性和开发知识，具备发现和解决与原生效果不一致的能力，掌握与原生通信的方法。
- RN上手难度：紧急情况下，抽调前端开发人员临时加入项目，上手时间大约1天左右可以参与进来

-- 开发效率 复用页面较多，两端开发效率均比较高
-- 编码难度 页面相对单一，两端编码难度均不大
-- 成果对比两者基本一致，可以做到和设计图一致
-- 功能性先以上传图片为例：安卓集成此功能后，RN也可以调用；反之，RN集成，安卓不可以用。RN在压缩图片等功能上因第三方限制会有一定局限性。
-- UI支持：列表、时间选择、表格、单选框、复选框、渐变色、徽标数（徽章）等，两端都支持，效果可做到一致。
-- 性能上看安卓会略优于RN，用户体验基本一致
-- 局限性：这里实际上RN页面只属于原生的一个“组件”，需要配套一系列的方法控制隐藏/显示一些安卓控件，让RN页面看起来是单独的页面，
          这会使得双方在互相通信上都投入了不小的成本。所以在实际开发中应尽力避免此种情况，RN应使用单独的Activity。
-- 总结：双方从开发成本、性能等方面做了对比，基本差距不大；但是因为此套方案的局限性，不建议在项目中的安卓页面嵌入RN。这里推荐使用原生开发，
        如果绩效考核不作为主页面的一部分，可以使用RN开发

场景二、安卓提供RN页面载体

- 举例1：以智管平台水源采集模块为例，核心技术是高德地图、抽屉、BottomSheetBehavior(底部可拖动弹窗)、上传图片
- 人员知识储备：能够熟练开发android页面，掌握Behavior的使用，掌握高德地图的使用、掌握集成RN开发环境和与RN的通信方法；
               熟练掌握RN的特性和开发知识，掌握高德地图的使用、具备发现和解决与原生效果不一致的能力，掌握与原生通信的方法。
- RN上手难度：需要有RN BottomSheetBehavior和高德地图开发经验，或者具备一定学习和解决问题能力。

-- 编码难度：Android方面主要难度在于1.高德地图的路线规划和导航 2.BottomSheetBehavior的使用 3.表单页面逻辑较为复杂，判断和接口数量比较多
            RN方面主要难度：1.BottomSheetBehavior的使用 2.表单页面逻辑较为复杂，判断和接口数量比较多，效果难以和原生一致
            二者编码都有一定难度
-- 开发效率：无复用页面，页面逻辑复杂，且编码难度较高，所以二者开发效率均比较低
-- 成果对比：页面较为复杂，体验和性能上Native可能会优于RN
-- 功能性：以高德地图为例，RN由于高德地图的sdk不支持路线规划和导航，只能由原生开发，然后跳转原生页面，该部分功能需要依赖原生。
-- UI支持：表单、输入框、下拉框、上传图片UI组件、BottomSheetBehavior、抽屉等，两端都支持，但效果难以做到一致。
-- 性能：由于功能复杂，安卓会优于RN，用户体验优于RN。
-- 局限性：RN无法实现导航页面，需要原生辅助开发。
-- 总结：从开发成本上来看，双方差距不大，从性能对比，安卓会优于RN；综合考量推荐使用RN+原生或者原生开发

### 混合开发的项目整体优缺点分析
#### 优点：
- 开发效率高，节约时间同一套代码Android和IOS基本都可用；
- RN更新和部署比较方便，增量更新使用成本较低
- 安卓端和前端的同学都可参与，进一步提高效率
- 本地调试修改代码后可以很快的下发，原生需要重新运行
#### 缺点：
- 用户体验会比较原生应用有些许差距
- 目前版本是0.62.3距离正式版1.XX还有一段时间的更新维护，技术还不是非常成熟
- 如果列表页有超过1000+的数据，因为RN没有复用机制，可能会有卡顿

### 根据优缺点选择项目架构
- 如果客户IOS和Android都需要，选用RN为项目主体。此框架下RN为主要开发者，原生作为辅助，制作工具类等。
- 如果仅需要Android，选用Android为项目主体。此框架下Android为主要开发者，RN可以负责子模块开发。


### 原生与rn互相通信
一、页面跳转
1.RN跳转原生
- android可以在module类中(AndroidInfo)暴露方法，来实现。参考AndroidInfo中的startActivityRN方法
2.原生跳转rn
- 承载rn页面的activity在项目中有且只有一个，即MyReactActivity
- （此方法已舍弃，rn加载效果不理想）APP中唤起RN页面的Activity，并将路由信息通过linking传递到对应的js中.注意事项：1)manifest中MyReactActivity的配置  2)MainActivity中的跳转方法和路由信息
- LinkUrl类中放和rn同学约定好的页面路由名称，跳转时修改，然后把传参保存到AndroidInfo中的map，rn端再调用getIntentData方法获取

二、方法调用
方法调用大致分为2种情况：
- Android主动向JS端传递事件、数据
- JS端主动向Android询问获取事件、数据

1) RCTDeviceEventEmitter 事件方式: 可任意时刻传递，Native主导控制，参考AndroidInfo中的sendDataToJS方法
2) Callback 回调方式: JS调用，Native返回(异步操作)；参考AndroidInfo中的selectPic
3) Promise: JS调用，Native返回。
4) 直传常量数据（原生向RN）: 跨域传值，只能从原生端向RN端传递。Native写法参考AndroidInfo中的getConstants方法

三、约束
- n和native彼此跳转避免出现三级跳转，比如rn跳转android，android再跳另一个rn页面。此类情况需要避免因为承载rn的MyReactActivity在项目中唯一
- rn和native不能都引入同一个第三方sdk，如有需要，解决方法是native开发，rn调用
- 公共变量、状态管理由native完成
  
四 、安卓原生与react-native交互
- import { NativeModules } from 'react-native' 引入
-  RN调用安卓方法: NavtiveModules.javaClass.Method/property
   eg: NativeModules.AndroidInfo.showBar()
- RN引入第三方插件
    1.yarn add react-native-linear-gradient
    2.根目录setting.gradle内注册  ':react-native-linear-gradient' 'project(':react-native-linear-gradient').projectDir = new File(rootProject.projectDir, './node_modules/react-native-linear-gradient/android')'
    3.目录app/build.gradle  dependencies对象内注册 implementation project(':rnlibs:react-native-linear-gradient')
    4.目录/app/src/main/java/com.ruowei.firewzlc.reactnative/PackageList import rnlibs/react-native-linear-gradient/src/.*/LinearGradientPackage(主类)
    (此类最终是要注册到安卓原生组件MainActivity 的 onCreate方法内 )










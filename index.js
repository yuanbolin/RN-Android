/*入口路由文件*/
import * as React from "react";
import { AppRegistry, NativeModules,AppState } from "react-native";
import { NavigationContainer } from "@react-navigation/native";
import {
  createStackNavigator,
  TransitionPresets,
  CardStyleInterpolators,
} from "@react-navigation/stack";
import { Provider } from "@ant-design/react-native";

//导航工具
import { navigationRef } from "./react-native/utils/RootNavigation";

import Head from "./react-native/utils/Head";
import Index from "./react-native/pages/index";
import Index2 from "./react-native/pages/index2";

const Stack = createStackNavigator();
const config = {
  animation: "spring",
  config: {
    stiffness: 1000,
    damping: 500,
    mass: 3,
    overshootClamping: true,
    restDisplacementThreshold: 0.01,
    restSpeedThreshold: 0.01,
  },
};

//隐藏标题栏
const getOptions = () => {
  return {
    headerShown: false,
  };
};


let url=NativeModules.AndroidInfo.LINK_URL

 function App() {
     console.log(url)
  return (
    <Provider>
      <NavigationContainer ref={navigationRef}>
        <Stack.Navigator
          screenOptions={({ route, navigation }) => ({
            header: (props) => <Head {...props} />,
            gestureEnabled: true,
            gestureDirection: "horizontal",
            cardOverlayEnabled: true,
            headerStatusBarHeight:
              navigation.dangerouslyGetState().routes.indexOf(route) > 0
                ? 0
                : undefined,
            cardStyleInterpolator: CardStyleInterpolators.forHorizontalIOS,
            headerStyleInterpolator:
              TransitionPresets.SlideFromRightIOS.headerStyleInterpolator,
            transitionSpec: {
              open: config,
              close: config,
            },
          })}
          initialRouteName={url}
        >
          <Stack.Screen
            name="Index"
            options={{
              title: "你好,世界!",
            }}
            component={Index}
          />
          <Stack.Screen
            name="Test"
            options={{
              title: "你成功跳转!",
            }}
            component={Index2}
          />
        </Stack.Navigator>
      </NavigationContainer>
    </Provider>
  );
}
console.disableYellowBox = true;
AppRegistry.registerComponent("BaseAndroid", () => App);

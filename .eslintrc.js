//.eslintrc.js
module.exports = {
  //此项是用来指定eslint解析器的，解析器必须符合规则，babel-eslint解析器是对babel解析器的包装使其与ESLint解析
  "parser": "babel-eslint",
  // 此项指定环境的全局变量，
  "env": {
    "es6": true,
    "node": true,
    "react-native/react-native": true,
  },
  // 脚本在执行期间访问的额外的全局变量
  // 当访问未定义的变量时，no-undef 规则将发出警告。如果你想在一个文件里使用全局变量，推荐你定义这些全局变量，这样 ESLint 就不会发出警告了。你可以使用注释或在配置文件中定义全局变量。
  "globals": {
    "px2dp": true,
    "FONT_SIZE": true,
    "SCREEN_WIDTH": true,
    "SCREEN_HEIGHT": true,
    "IOS": true,
    "Android": true,
    "Toast": true,
    "isIPhoneX": true,
  },
  // 此项是用来配置标准的js风格，就是说写代码的时候要规范的写，
  "extends": [
    'airbnb',
    'plugin:compat/recommended',
    'prettier',
  ],
  // 设置解析器选项（必须设置这个属性）
  "parserOptions": {
    // 想使用的额外的语言特性:
    "ecmaFeatures": {
      // http://es6.ruanyifeng.com/#docs/object#对象的扩展运算符
      "experimentalObjectRestSpread": true,
      // 启用 JSX
      "jsx": true
    },
    //设置"script"（默认）或"module"如果你的代码是在ECMAScript中的模块
    "sourceType": "module"
  },
  // 此项是用来提供插件的，插件名称省略了eslint-plugin-，
  "plugins": [
    "prettier",
    "react",
    "react-native",
    "flowtype"
  ],
  /**
   * "off" 或 0 - 关闭规则
   * "warn" 或 1 - 开启规则，使用警告级别的错误：warn (不会导致程序退出),
   * "error" 或 2 - 开启规则，使用错误级别的错误：error (当被触发的时候，程序会退出)
   */
  "rules": {
    ////////////////
    // 可能的错误 //
    ////////////////
    //规定对象属性换行时注意统一代码风格(要么都换行，要么都不换)。第二个参数是一个对象，配置是否允许在一行代码中书写多个属性。
    "no-use-before-define": 0,
    "object-property-newline": ["error", { "allowMultiplePropertiesPerLine": true }],
    "no-inline-comments": 0,//禁止行内备注
    "spaced-comment": 0,//注释风格,要不要有空格什么的
    "lines-around-comment": 0,//行前/行后备注
    "no-trailing-spaces": 0,  //一行结束后面 空格
    "no-multi-spaces": 1, //多余 空格
    "no-mixed-spaces-and-tabs" : 0, // 混用tab space
    "react/no-access-state-in-setstate": 0,
    "no-plusplus": 0,
    "react/no-array-index-key": 0,
    "prefer-const":0,
    "consistent-return": 0,
    // allow paren-less arrow functions
    "global-require": 0 ,
    "react/destructuring-assignment": [0,'never',{
      "ignoreClassFields": true
    }],
    'prettier/prettier': ['error',{
      htmlWhitespaceSensitivity: 'ignore',
      singleQuote: true
    }],
    "prefer-destructuring": ["error", {
      "array": false,
      "object": false
    }, {
      "enforceForRenamedProperties": false
    }],
    "camelcase": 0, //强制驼峰法命名
    'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'react/jsx-filename-extension': [1, { extensions: ['.js'] }],
    'react/jsx-wrap-multilines': 0,
    'react/prop-types': 0,
    'react/forbid-prop-types': 0,
    'react/jsx-one-expression-per-line': 0,
    'import/no-unresolved': [1, { ignore: ['^@/', '^umi/'] }],
    "no-unused-vars": [1, {
      // 允许声明未使用变量
      "vars": "local",
      // 参数不检查
      "args": "none"
    }],
    'import/no-extraneous-dependencies': [
      2,
      {
        optionalDependencies: true,
        devDependencies: ['**/tests/**.js', '/mock/**/**.js', '**/**.test.js'],
      },
    ],
    'import/no-cycle': 0,
    'jsx-a11y/no-noninteractive-element-interactions': 0,
    'jsx-a11y/click-events-have-key-events': 0,
    'jsx-a11y/no-static-element-interactions': 0,
    'jsx-a11y/anchor-is-valid': 0,
    "jsx-a11y/label-has-associated-control": "off",
    "jsx-a11y/label-has-for":"off",
    'linebreak-style': 0,

    // 禁止不必要的布尔转换
    "no-extra-boolean-cast": 0,
    "no-useless-computed-key": 0,
    // 禁止不必要的括号 //(a * b) + c;//报错
    "no-extra-parens":0,
    // 禁止不必要的分号
    // 禁止在字符串和注释之外不规则的空白
    // "no-irregular-whitespace":2,
    // 禁止出现令人困惑的多行表达式
    "no-unexpected-multiline":2,
    // 禁止在return、throw、continue 和 break语句之后出现不可达代码
    /*
     function foo() {
     return true;
     console.log("done");
     }//错误
     */
    "no-unreachable":2,
    // 强制 typeof 表达式与有效的字符串进行比较
    // typeof foo === "undefimed" 错误
    "valid-typeof":2,


    // 启用严格模式
    'strict': 'error',
    // 空格方式,使用tab

    // 允许使用 单引号和es6的``
    "quotes": [
      "error",
      "single",
      { "allowTemplateLiterals": true }
    ],
    // 要求或禁止使用分号而不是 ASI（这个才是控制行尾部分号的，）
    "semi": [
      0,
      "always"
    ],
    // 尽可能使用`===`
    "eqeqeq": 2,
    // 强制在代码块中开括号前和闭括号后有空格
    "block-spacing": [2, "always"],
    // 在代码块之前强制使用空格
    "space-before-blocks": 2,
    // 要求操作符周围有空格
    "space-infix-ops": 2,
    // 一元操作符必须要有空格
    "space-unary-ops": 2,
    // 强制在注释中 // 或 /* 使用一致的空格
    // "spaced-comment": [2, "always", { exceptions: ["-"] }],
    // 强制关键字周围空格的一致性
    "keyword-spacing": [
      2,
      { "before": true, "after": true }
    ],
    'arrow-parens': 0,
    // 强制在箭头函数中 "xxx() => {}"
    "arrow-spacing": [
      "error",
      { "before": true, "after": true }
    ],
    // 在冒号后要加上空格
    "key-spacing":[
      "error",
      { "beforeColon": false }
    ],
    // 不允许使用var
    "no-var": 1,
    // 要求在逗号后使用一个或多个空格
    "comma-spacing": [
      "error",
      { "after": true }
    ],
    // 不允许改变用const声明的变量
    'no-const-assign': 'error',
    // 如果一个变量不会被重新赋值，最好使用const进行声明。

    // 不允许使用行内样式
    "react-native/no-inline-styles": 0,
    // 强制类型后面要有一个","
    "flowtype/delimiter-dangle": [
      1,
      "only-multiline"
    ],
    // 在 : 后强制加空格
    "flowtype/space-after-type-colon": [
      2,
      "always"
    ],
    // 在 | & 符号中,强制加空格
    "flowtype/union-intersection-spacing": [
      2,
      "always"
    ],

  }
};

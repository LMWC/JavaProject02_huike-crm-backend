module.exports = {
  root: true,
  env: {
    node: true
  },
  extends: ['plugin:vue/recommended', 'eslint:recommended'],
  // 'extends': [
  //   'plugin:vue/essential',
  //   '@vue/standard'
  // ],
  "globals": {
    "joint": true,
    "App": true,
  },
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    "vue/html-self-closing": 'off',
    "vue/require-default-prop": 'off',
    "vue/no-template-shadow": 'off',
    "vue/max-attributes-per-line": [2, {
      "singleline": 10,
      "multiline": {
        "max": 1,
        "allowFirstLine": false
      }
    }],
    "vue/singleline-html-element-content-newline": "off",
    "vue/multiline-html-element-content-newline":"off",
    "vue/name-property-casing": ["error", "PascalCase"],
    "vue/no-v-html": "off",
    'accessor-pairs': 2,
    'arrow-spacing': [2, {
      'before': true,
      'after': true
    }],
    'block-spacing': [2, 'always'],
    'brace-style': [2, '1tbs', {
      'allowSingleLine': true
    }],
    'camelcase': [0, {
      'properties': 'always'
    }],
    'comma-dangle': [2, 'never'],
    'comma-spacing': [2, {
      'before': false,
      'after': true
    }],
    'comma-style': [2, 'last'],
    'constructor-super': 2,
    'curly': [2, 'multi-line'],
    'dot-location': [2, 'property'],
    'eol-last': 2,
    'eqeqeq': ["error", "always", {"null": "ignore"}],
    'generator-star-spacing': [2, {
      'before': true,
      'after': true
    }],
    'handle-callback-err': [2, '^(err|error)$'],
    'indent': [2, 2, {
      'SwitchCase': 1
    }],
    'jsx-quotes': [2, 'prefer-single'],
    'key-spacing': [2, {
      'beforeColon': false,
      'afterColon': true
    }],
    'keyword-spacing': [2, {
      'before': true,
      'after': true
    }],
    'new-cap': [2, {
      'newIsCap': true,
      'capIsNew': false
    }],
    'new-parens': 2,
    'no-array-constructor': 2,
    'no-caller': 2,
    'no-class-assign': 2,
    'no-cond-assign': 2,
    'no-const-assign': 2,
    'no-control-regex': 0,
    'no-delete-var': 2,
    'no-dupe-args': 2,
    'no-dupe-class-members': 2,
    'no-dupe-keys': 2,
    'no-duplicate-case': 2,
    'no-empty-character-class': 2,
    'no-empty-pattern': 2,
    'no-eval': 2,
    'no-ex-assign': 2,
    'no-extend-native': 2,
    'no-extra-bind': 2,
    'no-extra-boolean-cast': 2,
    'no-extra-parens': [2, 'functions'],
    'no-fallthrough': 2,
    'no-floating-decimal': 2,
    'no-func-assign': 2,
    'no-implied-eval': 2,
    'no-inner-declarations': [2, 'functions'],
    'no-invalid-regexp': 2,
    'no-irregular-whitespace': 2,
    'no-iterator': 2,
    'no-label-var': 2,
    'no-labels': [2, {
      'allowLoop': false,
      'allowSwitch': false
    }],
    'no-lone-blocks': 2,
    'no-mixed-spaces-and-tabs': 2,
    'no-multi-spaces': 2,
    'no-multi-str': 2,
    'no-multiple-empty-lines': [2, {
      'max': 1
    }],
    'no-native-reassign': 2,
    'no-negated-in-lhs': 2,
    'no-new-object': 2,
    'no-new-require': 2,
    'no-new-symbol': 2,
    'no-new-wrappers': 2,
    'no-obj-calls': 2,
    'no-octal': 2,
    'no-octal-escape': 2,
    'no-path-concat': 2,
    'no-proto': 2,
    'no-redeclare': 2,
    'no-regex-spaces': 2,
    'no-return-assign': [2, 'except-parens'],
    'no-self-assign': 2,
    'no-self-compare': 2,
    'no-sequences': 2,
    'no-shadow-restricted-names': 2,
    'no-spaced-func': 2,
    'no-sparse-arrays': 2,
    'no-this-before-super': 2,
    'no-throw-literal': 2,
    'no-trailing-spaces': 2,
    'no-undef': 2,
    'no-undef-init': 2,
    'no-unexpected-multiline': 2,
    'no-unmodified-loop-condition': 2,
    'no-unneeded-ternary': [2, {
      'defaultAssignment': false
    }],
    'no-unreachable': 2,
    'no-unsafe-finally': 2,
    // 'no-unused-vars': [2, {
    //   'vars': 'all',
    //   'args': 'none'
    // }],
    'no-useless-call': 2,
    'no-useless-computed-key': 2,
    'no-useless-constructor': 2,
    'no-useless-escape': 0,
    'no-whitespace-before-property': 2,
    'no-with': 2,
    'one-var': [2, {
      'initialized': 'never'
    }],
    'operator-linebreak': [2, 'after', {
      'overrides': {
        '?': 'before',
        ':': 'before'
      }
    }],
    'padded-blocks': [2, 'never'],
    'quotes': [2, 'single', {
      'avoidEscape': true,
      'allowTemplateLiterals': true
    }],
    'semi': [2, 'never'],
    'semi-spacing': [2, {
      'before': false,
      'after': true
    }],
    'space-before-blocks': [2, 'always'],
    // 'space-before-function-paren': [2, 'never'],
    'space-in-parens': [2, 'never'],
    'space-infix-ops': 2,
    'space-unary-ops': [2, {
      'words': true,
      'nonwords': false
    }],
    'spaced-comment': [2, 'always', {
      'markers': ['global', 'globals', 'eslint', 'eslint-disable', '*package', '!', ',']
    }],
    'template-curly-spacing': [2, 'never'],
    'use-isnan': 2,
    'valid-typeof': 2,
    'wrap-iife': [2, 'any'],
    'yield-star-spacing': [2, 'both'],
    'yoda': [2, 'never'],
    // 'prefer-const': 2,
    'object-curly-spacing': [2, 'always', {
      objectsInObjects: false
    }],
    'array-bracket-spacing': [2, 'never'],

     /**
     * 代码中可能的错误或逻辑错误
     */
    "no-cond-assign": ["error", "always"], // 禁止条件表达式中出现赋值操作符
    // "no-console": ["error", { allow: ["warn", "error"] }], // 禁用 console
    "no-constant-condition": ["error", { "checkLoops": true }], // 禁止在条件中使用常量表达式
    "no-control-regex": ["error"], // 禁止在正则表达式中使用控制字符
    // "no-debugger": ["error"], // 禁用 debugger
    "no-dupe-args": ["error"], // 禁止 function 定义中出现重名参数
    "no-dupe-keys": ["error"], // 禁止对象字面量中出现重复的 key
    "no-duplicate-case": ["error"], // 禁止出现重复的 case 标签
    "no-empty": ["error", { "allowEmptyCatch": true }], // 禁止出现空语句块
    "no-empty-character-class": ["error"], // 禁止在正则表达式中使用空字符集
    "no-ex-assign": ["error"], // 禁止对 catch 子句的参数重新赋值
    "no-extra-boolean-cast": ["error"], // 禁止不必要的布尔转换
    "no-extra-semi": ["error"], // 禁止不必要的分号
    "no-func-assign": ["warn"], // 禁止对 function 声明重新赋值
    "no-inner-declarations": ["error"], // 禁止在嵌套的块中出现变量声明或 function 声明
    "no-invalid-regexp": ["error", { "allowConstructorFlags": [] }], // 禁止 RegExp 构造函数中存在无效的正则表达式字符串
    "no-irregular-whitespace": ["error"], // 禁止在字符串和注释之外不规则的空白
    "no-obj-calls": ["error"], // 禁止把全局对象作为函数调用
    "no-regex-spaces": ["error"], // 禁止正则表达式字面量中出现多个空格
    "no-sparse-arrays": ["error"], // 禁用稀疏数组
    "no-unexpected-multiline": ["error"], // 禁止出现令人困惑的多行表达式
    "no-unsafe-finally": ["error"], // 禁止在 finally 语句块中出现控制流语句
    "no-unsafe-negation": ["error"], // 禁止对关系运算符的左操作数使用否定操作符
    "use-isnan": ["error"], // 要求使用 isNaN() 检查 NaN

    /**
     * 最佳实践
     */
    "default-case": ["error"], // 要求 switch 语句中有 default 分支
    "dot-notation": ["error"], // 强制尽可能地使用点号
    "eqeqeq": ["warn"], // 要求使用 === 和 !==
    "no-caller": ["error"], // 禁用 arguments.caller 或 arguments.callee
    "no-case-declarations": ["error"], // 不允许在 case 子句中使用词法声明
    // "no-empty-function": ["error"], // 禁止出现空函数
    "no-empty-pattern": ["error"], // 禁止使用空解构模式
    "no-eval": ["error"], // 禁用 eval()
    "no-global-assign": ["error"], // 禁止对原生对象或只读的全局对象进行赋值
    // "no-magic-numbers": ["error", { "ignoreArrayIndexes": true }], // 禁用魔术数字
    "no-redeclare": ["error", { "builtinGlobals": true }], // 禁止重新声明变量
    // "no-self-assign": ["error", { props: true }], // 禁止自我赋值
    "no-unused-labels": ["error"], // 禁用出现未使用过的标
    // "no-useless-escape": ["error"], // 禁用不必要的转义字符
    // "radix": ["error"], // 强制在parseInt()使用基数参数

    /**
     * 变量声明
     */
    "no-delete-var": ["off"], // 禁止删除变量
    "no-undef": ["off"], // 禁用未声明的变量，除非它们在 /*global */ 注释中被提到
    "no-unused-vars": ["off"], // 禁止出现未使用过的变量
    "no-use-before-define": ["off"], // 禁止在变量定义之前使用它们

    /**
     * 风格指南
     */
    "array-bracket-newline": ["error", { "multiline": true }], // 在数组开括号后和闭括号前强制换行
    "array-bracket-spacing": ["error", "never"], // 强制数组方括号中使用一致的空2
    // "block-spacing": ["error", "never"], // 禁止或强制在代码块中开括号前和闭括号后有空格
    "brace-style": ["error", "1tbs",], // 强制在代码块中使用一致的大括号风格
    "comma-dangle": ["error", "never"], // 要求或禁止末尾逗号
    "comma-spacing": ["error", { "before": false, "after": true }], // 强制在逗号前后使用一致的空格
    "comma-style": ["error", "last"], // 强制使用一致的逗号风格
    "computed-property-spacing": ["error", "never"], // 强制在计算的属性的方括号中使用一致的空格
    // "consistent-this": ["error", "that"], // 当获取当前执行环境的上下文时，强制使用一致的命名
    "eol-last": ["error", "always"], // 要求或禁止文件末尾存在空行
    "func-call-spacing": ["error", "never"], // 要求或禁止在函数标识符和其调用之间有空格
    // "func-names": ["error", "always"], // 要求或禁止使用命名的 function 表达式
    "func-style": ["error", "declaration", { "allowArrowFunctions": true }], // 强制一致地使用 function 声明或表达式
    "function-paren-newline": ["error", "multiline"], // 强制在函数括号内使用一致的换行
    "implicit-arrow-linebreak": ["error", "beside"], // 强制隐式返回的箭头函数体的位置
    "indent": ["error", 2, { "SwitchCase": 1 }], // 两个空格缩进
    "jsx-quotes": ["error", "prefer-double"], // 强制在 JSX 属性中一致地使用双引号或单引号
    "key-spacing": ["error", { "beforeColon": false, "afterColon": true }], // 强制在对象字面量的属性中键和值之间使用一致的间距
    // "line-comment-position": ["error", { "position": "above", "ignorePattern": "ETC" }], // 强制行注释的位置
    // "linebreak-style": ["error", "unix"], // 换行符风格
    // "max-depth": ["error", 4], // 强制可嵌套的块的最大深度
    // "max-nested-callbacks": ["error", 3], // 强制回调函数最大嵌套深度
    // "max-params": ["error", 6], // 强制函数定义中最多允许的参数数量
    // "multiline-comment-style": ["error", "starred-block"], // 强制对多行注释使用特定风格
    // "multiline-ternary": ["error", "always-multiline"], // 要求或禁止在三元操作数中间换行
    "new-cap": ["error", { "capIsNew": false }], // 要求构造函数首字母大写
    // "no-array-constructor": ["error"], // 禁用 Array 构造函数
    // "no-mixed-operators": ["error"], // 禁止混合使用不同的操作符
    "no-mixed-spaces-and-tabs": ["error"], // 禁止空格和 tab 的混合缩进
    "no-multiple-empty-lines": ["error"], // 禁止出现多行空行
    // "no-new-object": ["error"], // 禁用 Object 的构造函数
    "no-tabs": ["error"], // 禁用 tab
    "no-trailing-spaces": ["error", { "skipBlankLines": false, "ignoreComments": false }], // 禁用行尾空白
    "no-whitespace-before-property": ["error"], // 禁止属性前有空白
    "nonblock-statement-body-position": ["error", "beside"], // 强制单个语句的位置
    "object-curly-spacing": ["error", "always"], // 强制在大括号中使用一致的空格
    "operator-linebreak": ["error", "after"], // 强制操作符使用一致的换行符
    "quotes": ["error", "single"], // 使用单引号
    // "semi": ["error", "always"], // 要求或禁止使用分号代替 ASI
    "semi-spacing": ["error", { "before": false, "after": true }], // 强制分号之前和之后使用一致的空格
    "space-before-function-paren": ["error", "always"], // 强制在 function的左括号之前使用一致的空格
    "space-in-parens": ["error", "never"], // 强制在圆括号内使用一致的空格
    "space-infix-ops": ["error"], // 要求操作符周围有空格
    "space-unary-ops": ["error", { "words": true, "nonwords": false }], // 强制在一元操作符前后使用一致的空格
    "spaced-comment": ["error", "always"], // 强制在注释中 // 或 /* 使用一致的空格

    /**
     * ECMAScript 6
     */
    "arrow-spacing": ["error", { "before": true, "after": true }], // 强制箭头函数的箭头前后使用一致的空格
    "no-var": ["error"], // 要求使用 let 或 const 而不是 var
    "object-shorthand": ["error", "always"], // 要求或禁止对象字面量中方法和属性使用简写语法
    "prefer-arrow-callback": ["error", { "allowNamedFunctions": false }], // 要求回调函数使用箭头函数
  },
  parserOptions: {
    parser: 'babel-eslint'
  }
}
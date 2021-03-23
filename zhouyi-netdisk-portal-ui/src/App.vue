<template>
	<div id="app"><router-view /></div>
</template>

<style lang="less">
@font-face{
	font-family: 'PingFangSC-Semibold'; 
	src: url('./assets/font/PingFang-SC-Semibold.otf'); 
	font-weight: normal;
	font-style: normal;
}
@font-face{
	font-family: 'PingFangSC-Regular'; 
	src: url('./assets/font/PingFang-SC-Regular.ttf'); 
	font-weight: normal;
	font-style: normal;
}
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
	font-family: PingFangSC-Regular, Microsoft YaHei, SimHei, Tahoma !important;
}
.clearfix{zoom:1}
.clearfix:after{content:"\20";display:block;height:0;clear:both;visibility:hidden}
ul,
ol {
	list-style: none;
}

</style>

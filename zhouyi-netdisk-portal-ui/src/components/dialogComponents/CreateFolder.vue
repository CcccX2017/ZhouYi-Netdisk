<template>
	<!-- 新建文件夹 -->
	<el-dialog title="新建文件夹" :visible.sync="visible" width="350px" custom-class="folderDialog" :close-on-click-modal="false">
		
	</el-dialog>
</template>

<script>
export default {
	data(){
		return{
			
		}
	},
	props: {
		visible: {
			type: Boolean,
			default: false
		}
	},
	methods:{
		
	}
};
</script>

<style></style>

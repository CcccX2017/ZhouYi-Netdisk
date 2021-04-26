<template>
    <div id="uploader-panel" v-show="isUpload">
        <uploader>
            <uploader-unsupport></uploader-unsupport>
            <uploader-list>
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span>文件列表</span>
                        <div style="float: right" class="operation">
                            <el-button type="text" icon="el-icon-minus" class="operation-btn"
                                       @click="minimize" v-if="isMaximize"></el-button>
                            <el-button type="text" icon="el-icon-full-screen" class="operation-btn" @click="maximize" v-else></el-button>
                            <el-button type="text" icon="el-icon-close" class="operation-btn" @click="closeUploadPanel"></el-button>
                        </div>
                    </div>
                    <div class="file-list" :style="fileListHeight">
                        <ul class="fileContainer" id="fileContainer">
                            <li class="file-info" v-for="item in 20">
                                文件{{ item }}
                            </li>
                        </ul>
                    </div>
                </el-card>
            </uploader-list>
        </uploader>
    </div>
</template>

<script>
    export default {
        name: "index",
        data() {
            return {
                fileListHeight: "height: 350px",
                isMaximize: true,
                isUpload: true
            }
        },
        created() {
        },
        methods: {
            // 关闭上传组件
            closeUploadPanel(){
                this.isUpload = false
            },
            // 最小化
            minimize() {
                this.fileListHeight = "height: 0"
                setTimeout(() => {
                    this.isMaximize = false
                },500)
            },
            // 最大化
            maximize(){
                this.fileListHeight = "height: 350px"
                setTimeout(() => {
                    this.isMaximize = true
                },500)
            }
        }
    }
</script>

<style lang="less">
    #uploader-panel {
        position: fixed;
        right: 20px;
        bottom: 0;
        z-index: 20;
        
        .box-card {
            width: 720px;
            
            .operation {
                .operation-btn {
                    padding: 0;
                    font-size: 18px;
                    font-weight: bold;
                }
            }
            
            .el-card__body {
                padding: 0;
                
                .file-list {
                    transition: height 0.5s;
                    transition-timing-function: linear;
                    overflow-x: hidden;
                    overflow-y: auto;
                    .fileContainer{
                        .file-info{
                            height: 50px;
                            line-height: 50px;
                            border-bottom: 1px solid rgba(179, 216, 255, 0.2);
                        }
                    }
                }
            }
        }
    }
</style>

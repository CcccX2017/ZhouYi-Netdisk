<template>
    <div class="container">
        <div class="aside">
            <ul v-for="val in menu" :key="val.menuId">
                <li class="submenu_title" :class="activeMenu == val.path ? 'active' : ''">
                    <a href="javascript:;" @click="changeView(val.path)">
                        <span class="text">
                            <i :class="val.iconCls" class="submenu_icon"></i>
                            <span class="text-inside">{{ val.name }}</span>
                        </span>
                    </a>
                </li>
            </ul>
            <div class="progress-bar">
                <el-progress :percentage="percentage" :color="customColor" :show-text="showText" :stroke-width="strokeWidth"></el-progress>
                <div style="display: flex;justify-content: space-between;align-items: center;margin-top: 5px;">
                    <span style="color: #424e67;font-size: 12px;line-height: 1.5">{{storageInfo}}</span>
                    <el-link type="primary" :underline="underline" style="font-size: 12px" title="扩容">扩容</el-link>
                </div>
            </div>
        </div>
        <div class="main">
            <router-view/>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'Netdisk',
        data() {
            return {
                menu: [],
                activeMenu: 'allFile',
                showText: false,
                underline: false,
                percentage: 0,
                strokeWidth: 8,
                customColor:[
                    {color: '#409eff', percentage: 60},
                    {color: '#e6a23c', percentage: 70},
                    {color: '#f56c6c', percentage: 100}
                ],
                storage: {
                    usedStorageSpace: '',
                    maxStorageSpace: ''
                }
            }
        },
        created() {
            this.initMenu()
            this.getStorage()
        },
        methods: {
            // 获取存储空间信息
            getStorage(){
                this.getRequest('/portal/user/storage').then(resp => {
                    this.percentage = resp.data.percent
                    this.storage.usedStorageSpace = resp.data.usedStorageSpace
                    this.storage.maxStorageSpace = resp.data.maxStorageSpace
                })
            },
            changeView(path){
                this.activeMenu = path
                this.$router.push(path)
            },
            initMenu() {
                this.activeMenu = this.$route.path.replace('/netdisk/','')
                this.menu = this.$store.getters.menus
            }
        },
        computed:{
            storageInfo(){
                return this.storage.usedStorageSpace + '/' + this.storage.maxStorageSpace
            }
        },
        watch: {
            '$route.path': function (val){
                this.activeMenu = val.replace('/netdisk/','')
            }
        }
    }
</script>

<style lang="less" scoped="scoped">
    .container {
        margin-top: 62px;
        
        .aside {
            position: absolute;
            width: 194px;
            top: 0;
            left: 0;
            bottom: 0;
            padding-top: 10px;
            
            .submenu_title:hover, .submenu_children:hover{
                background: rgba(0,0,0,.05);
            }
            
            .submenu_title a, .submenu_children a{
                display: block;
                height: 45px;
                line-height: 45px;
                padding-left: 15px;
                font-size: 14px;
                zoom: 1;
                .text{
                    position: relative;
                    display: block;
                    width: 115px;
                    height: 45px;
                    padding-left: 38px;
                    .submenu_icon{
                        display: inline-block;
                        line-height: 45px;
                        position: absolute;
                        left: 10px;
                        font-size: 16px;
                        font-weight: 700;
                    }
                }
            }
         
            ul{
                li{
                    &.active{
                        background: rgba(0, 0, 0, .05);
                        a{
                            .text{
                                color: #409EFF;
                                .submenu_icon, .text-inside{
                                    color: #409EFF;
                                }
                            }
                        }
                    }
                    a{
                        .text{
                            color: #666;
                            .submenu_icon, .text-inside{
                                color: #666;
                            }
                        }
                    }
                }
            }
            
            .progress-bar{
                position: absolute;
                width: 100%;
                bottom: 50px;
                padding: 0 20px;
            }
        }
        
        .main {
            position: absolute;
            background-color: #fff;
            left: 194px;
            top: 0;
            right: 0;
            bottom: 0;
        }
    }
</style>

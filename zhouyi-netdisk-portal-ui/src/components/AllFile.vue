<template>
  <div>
    <div class="file-fun-area">
      <div class="fun-btn">
        <el-button
          type="primary"
          icon="el-icon-upload2"
          round
          @click="uploader"
          size="small"
          class="uploaderBtn"
        >
          上传
        </el-button>
        <el-button
          type="primary"
          plain
          icon="el-icon-folder-add"
          class="plain-btn"
          @click="openCreateFolderDialog"
          size="small"
          round
        >
          新建文件夹
        </el-button>
        <el-button-group class="btn-group" v-if="btnGroup.show" size="small">
          <el-button
            type="primary"
            plain
            icon="iconfont icon-share"
            class="plain-btn"
            size="small"
            round
          >
            分享
          </el-button>
          <el-button
            type="primary"
            plain
            icon="el-icon-download"
            class="plain-btn"
            size="small"
          >
            下载
          </el-button>
          <el-button
            type="primary"
            plain
            icon="el-icon-delete"
            class="plain-btn"
            size="small"
            @click.native.stop="deleteFile"
          >
            删除
          </el-button>
          <el-button
            type="primary"
            plain
            class="plain-btn"
            :disabled="btnGroup.disabled"
            size="small"
            @click.native.stop="openRenameDialog"
            icon="el-icon-edit"
          >
            重命名
          </el-button>
          <el-button
            type="primary"
            plain
            class="plain-btn"
            size="small"
            icon="el-icon-document-copy"
            @click.native="openCopyOrMove('复制到')"
          >
            复制
          </el-button>
          <el-button
            type="primary"
            plain
            class="plain-btn"
            size="small"
            icon="el-icon-rank"
            round
            @click.native="openCopyOrMove('移动到')"
          >
            移动
          </el-button>
        </el-button-group>
      </div>
      <div class="right-fun clearfix">
        <div class="icon-fun-area">
          <div
            class="paixu-div"
            @mouseenter="paixuMouseenter"
            @mouseleave="paixuMouseleave"
          >
            <i class="iconfont icon-paixu paixu-i"></i>
            <ul
              class="paixu-ul"
              v-show="paixuUl"
              @mouseenter="paixuUlMouseenter"
              @mouseleave="paixuUlMouseleave"
            >
              <li class="paixu-li" @click.stop="changePaixu('name')">
                <span>
                  <em
                    class="el-icon-check"
                    v-show="queryParam.order == 'name'"
                  ></em>
                  文件名
                </span>
              </li>
              <li class="paixu-li" @click.stop="changePaixu('size')">
                <span>
                  <em
                    class="el-icon-check"
                    v-show="queryParam.order == 'size'"
                  ></em>
                  大小
                </span>
              </li>
              <li class="paixu-li" @click.stop="changePaixu('time')">
                <span>
                  <em
                    class="el-icon-check"
                    v-show="queryParam.order == 'time'"
                  ></em>
                  修改日期
                </span>
              </li>
            </ul>
          </div>
          <i
            class="iconfont icon-viewgallery paixu-i"
            v-if="isGridView"
            @click="changeView('gallery')"
          ></i>
          <i
            class="iconfont icon-viewlist paixu-i"
            v-else
            @click="changeView('list')"
          ></i>
        </div>
        <div class="fun-search clearfix">
          <el-input
            size="small"
            class="search-input"
            placeholder="搜索您的文件"
            v-model="searchParam.keyword"
            clearable
            @clear="clearKeyword"
            @keyup.enter.native="search"
          ></el-input>
          <i class="el-icon-search search-icon" @click="search"></i>
        </div>
      </div>
    </div>
    <div class="file-container">
      <div class="title-info clearfix">
        <span class="menu-tag">
          <span
            v-show="queryParam.dir === '/' && isSearch === 0"
            class="textSpan"
          >
            全部文件
          </span>
          <ul
            class="breadcrumbUl"
            v-show="queryParam.dir !== '/' || isSearch === 1"
          >
            <li>
              <el-link type="primary" @click.native="goBack(null)">
                返回上一级
              </el-link>
              <span
                style="color: #c5d8f3;padding: 0 3px;vertical-align: middle;"
              >
                |
              </span>
            </li>
            <li>
              <el-link
                type="primary"
                @click.native="goBack('/')"
                title="全部文件"
              >
                全部文件
              </el-link>
              <span style="color: #c5d8f3;padding: 0 5px">></span>
              <template v-for="(item, index) in breadcrumb">
                <div
                  v-show="isSearch === 0"
                  style="display: inline-block;"
                  :key="index"
                >
                  <el-link
                    type="primary"
                    v-if="index != breadcrumb.length - 1 && item.name != '...'"
                    @click="goBack(item.path)"
                    :title="item.name"
                  >
                    {{ item.name | breadcrumbFilter(breadcrumb.length) }}
                  </el-link>
                  <span class="breadcrumbTxtSpan" v-else :title="item.name">
                    {{ item.name | breadcrumbFilter(breadcrumb.length) }}
                  </span>
                  <span
                    style="color: #c5d8f3;padding: 0 5px"
                    v-if="index != breadcrumb.length - 1"
                  >
                    &gt;
                  </span>
                </div>
              </template>
              <span class="breadcrumbTxtSpan" v-show="isSearch === 1">
                {{ `搜索："${searchParam.keyword}"` }}
              </span>
            </li>
          </ul>
        </span>
        <span class="load-count textSpan" v-if="loading != null">
          获取更多数据...
        </span>
        <span class="load-count textSpan" v-else-if="isAll == 1">
          已全部加载，共{{ count }}个
        </span>
        <span class="load-count textSpan" v-else>已加载{{ count }}个</span>
      </div>
      <div class="file-list">
        <div class="list-title">
          <ul class="title-ul" v-if="isSearch == 0">
            <li
              data-key="name"
              style="width: 60%;padding-left: 16px;"
              @click.stop="changeOrder('name')"
            >
              <el-checkbox
                :indeterminate="isIndeterminate"
                v-model="checkAll"
                @change="handleCheckAllChange"
                @click.native="stopDefault($event)"
              ></el-checkbox>
              <span style="padding-left: 10px;">{{ tableTitle }}</span>
              <i
                :class="queryParam.desc == 1 ? 'el-icon-bottom' : 'el-icon-top'"
                class="sortIcon"
                v-if="queryParam.order == 'name'"
              ></i>
            </li>
            <li
              data-key="size"
              style="width: 16%;"
              @click.stop="changeOrder('size')"
            >
              <span>大小</span>
              <i
                :class="queryParam.desc == 1 ? 'el-icon-bottom' : 'el-icon-top'"
                class="sortIcon"
                v-if="queryParam.order == 'size'"
              ></i>
            </li>
            <li
              data-key="time"
              style="width: 23%;"
              @click.stop="changeOrder('time')"
            >
              <span>修改日期</span>
              <i
                :class="queryParam.desc == 1 ? 'el-icon-bottom' : 'el-icon-top'"
                class="sortIcon"
                v-if="queryParam.order == 'time'"
              ></i>
            </li>
          </ul>
          <ul class="title-ul" v-else>
            <li data-key="name" style="width: 60%;padding-left: 16px;">
              <el-checkbox
                :indeterminate="isIndeterminate"
                v-model="checkAll"
                @change="handleCheckAllChange"
                @click.native="stopDefault($event)"
              ></el-checkbox>
              <span style="padding-left: 10px;">{{ tableTitle }}</span>
            </li>
            <li data-key="size" style="width: 16%;"><span>大小</span></li>
            <li data-key="time" style="width: 13%;"><span>修改日期</span></li>
            <li data-key="time" style="width: 10%;"><span>所在目录</span></li>
          </ul>
        </div>
        <div id="fileDiv" class="list-content" :style="content">
          <vue-scroll
            ref="vs"
            style="top: -1px;border: none;"
            @handle-scroll="handleScroll"
          >
            <template v-for="(item, index) in list">
              <div
                class="content-row clearfix"
                :key="index + new Date().getTime()"
                @click.prevent="checkOne(item)"
                :class="index == 0 ? 'first' : ''"
              >
                <div
                  class="left content-col content-flex"
                  style="width: 60%;padding-left: 16px;"
                >
                  <el-checkbox-group v-model="checkedList">
                    <el-checkbox
                      :label="item.id"
                      @click.native="stopDefault($event)"
                      @change="(checked) => checkMore(checked, item)"
                    >
                      {{ '' }}
                    </el-checkbox>
                  </el-checkbox-group>
                  <img :src="require('../assets/filetype/' + item.icon)" />
                  <span class="txtSpan" style="padding-left: 10px;">
                    <a
                      href="javascript:;"
                      v-if="item.isDir"
                      @click.stop="openDir(item.path)"
                    >
                      {{ item.name }}
                    </a>
                    <span v-else>{{ item.name }}</span>
                  </span>
                </div>
                <div class="left content-col" style="width: 16%">
                  <span class="txtSpan">{{ item.sizeStr }}</span>
                </div>
                <div
                  class="left content-col"
                  style="width: 23%"
                  v-if="isSearch == 0"
                >
                  <span class="txtSpan">{{ item.gmtModified }}</span>
                </div>
                <template v-else>
                  <div class="left content-col" style="width: 13%">
                    <span class="txtSpan">{{ item.gmtModified }}</span>
                  </div>
                  <div class="left content-col" style="width: 10%">
                    <span class="txtSpan">
                      <a
                        href="javascript:;"
                        class="dirLink"
                        :title="assembleDirectory(item.dir)"
                        @click.stop="goBack(item.dir)"
                      >
                        {{ assembleDirectory(item.dir) | directoryFilter }}
                      </a>
                    </span>
                  </div>
                </template>
              </div>
            </template>
          </vue-scroll>
        </div>
      </div>
    </div>

    <!-- 新建文件夹 -->
    <CreateFolder
      :visible.sync="dialogFolderVisible"
      :dir="queryParam.dir"
      @resetQueryParam="resetQueryParam"
      @getList="getList"
    ></CreateFolder>

    <!-- 重命名 -->
    <RenameCom
      :visible.sync="dialogRenameVisible"
      :checkedFileName="checkedFileName"
      :checkList="checkList"
      :dir="queryParam.dir"
      @resetQueryParam="resetQueryParam"
      @resetCheckedList="resetCheckedList"
      @getList="getList"
    ></RenameCom>

    <CopyOrMove
      :title="copyOrMoveVisibleTitle"
      :visible.sync="copyOrMoveVisible"
    ></CopyOrMove>
  </div>
</template>

<script>
import { Loading } from 'element-ui'
import bus from '@/utils/bus.js'
import CreateFolder from '@/components/dialogComponents/CreateFolder'
import RenameCom from '@/components/dialogComponents/Rename'
import CopyOrMove from '@/components/dialogComponents/CopyOrMove'
import { generateBreadcrumb } from '@/utils/breadcrumb.js'

export default {
  name: 'AllFile',
  components: {
    CopyOrMove,
    CreateFolder,
    RenameCom,
  },
  data() {
    return {
      copyOrMoveVisibleTitle: '',
      copyOrMoveVisible: false,
      dialogFolderVisible: false,
      checkAll: false,
      isIndeterminate: false,
      isSearch: 0,
      queryParam: {
        page: 1,
        limit: 100,
        order: 'time',
        desc: 0,
        dir: '/',
      },
      searchParam: {
        page: 1,
        limit: 100,
        order: 'time',
        desc: 1,
        keyword: '',
      },
      list: [],
      count: null,
      isAll: null,
      checkedList: [],
      btnGroup: {
        show: false,
        disabled: false,
      },
      content: {
        height: '',
      },
      loading: null,
      // 面包屑导航数组
      breadcrumb: [],
      // 选中的文件列表
      checkList: {
        fileIds: [],
        folderIds: [],
      },
      // 选择的文件名称，供重命名使用
      checkedFileName: '',
      dialogRenameVisible: false,
      // 是否显示排序框
      paixuUl: false,
      // 鼠标是否在排序框内
      mouseInUl: false,
      // 鼠标是否在排序图标上
      mouseInDiv: false,
      isGridView: true,
    }
  },
  created() {
    window.addEventListener('resize', this.transferHeight)
    this.transferHeight()
    this.initOrder()
  },
  mounted() {
    this.getList()
  },
  computed: {
    tableTitle() {
      let count = this.checkedList.length
      if (count === 0) {
        return '文件名'
      }
      return `已选中${count}个文件/文件夹`
    },
  },
  methods: {
    openCopyOrMove(title) {
      this.copyOrMoveVisibleTitle = title
      this.copyOrMoveVisible = true
    },
    resetCheckedList() {
      this.checkedFileName = ''
      this.checkedList = []
      this.checkList.fileIds = []
      this.checkList.folderIds = []
    },
    changeView(view) {
      if (view === 'list') {
        this.isGridView = true
      } else if (view === 'gallery') {
        this.isGridView = false
      }
    },
    // 上传文件
    uploader() {
      bus.$emit('openUploader', {
        path: this.assembleDirectory(this.queryParam.dir),
        targetPath: this.queryParam.dir,
      })
    },
    // 重命名弹出框
    openRenameDialog() {
      this.dialogRenameVisible = true
    },
    // 组装所在目录
    assembleDirectory(dir) {
      if (dir == '/') {
        return '全部文件'
      }
      let dirArr = dir.split('/')
      return dirArr[dirArr.length - 1]
    },
    // 清空搜索条件
    clearKeyword() {
      this.searchParam.keyword = ''
      this.isSearch = 0
      this.resetQueryParam()
      this.getList()
    },
    // 条件搜索
    search() {
      if (this.searchParam.keyword) {
        this.list = []
        this.isSearch = 1
        this.searchParam.page = 1
        this.breadcrumb = [`搜索："${this.searchParam.keyword}"`]
        this.getList()
      }
    },
    // 删除选中的文件
    deleteFile() {
      this.$confirm(
        `确认要把所选文件放入回收站吗?<br>删除的文件可在15天内通过回收站还原`,
        '删除确认',
        {
          dangerouslyUseHTMLString: true,
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }
      )
        .then(() => {
          this.deleteRequest('/portal/list/', this.checkList).then((resp) => {
            if (resp) {
              this.resetQueryParam()
              this.checkedList = []
              this.checkList.fileIds = []
              this.checkList.folderIds = []
              this.getList()
            }
          })
        })
        .catch(() => {})
    },
    // 面包屑导航
    goBack(path) {
      if (this.isSearch == 1) {
        this.isSearch = 0
      }
      if (path) {
        this.queryParam.dir = path
      } else {
        // 返回上一级
        let index = this.queryParam.dir.lastIndexOf('/')
        if (index === 0) {
          this.queryParam.dir = '/'
        } else {
          this.queryParam.dir = this.queryParam.dir.substr(0, index)
        }
      }
      this.assembleDir(this.queryParam.dir)
      this.resetQueryParam()
      this.getList()
    },
    // 重置参数
    resetQueryParam() {
      this.queryParam.page = 1
    },
    // 打开新建文件夹弹出框
    openCreateFolderDialog() {
      this.dialogFolderVisible = true
    },
    // loading效果
    startLoading() {
      this.loading = Loading.service({ target: '#fileDiv', fullscreen: false })
    },
    // 关闭loading效果
    closeLoading() {
      if (this.loading) {
        this.$nextTick(() => {
          this.loading.close()
          this.loading = null
        })
      }
    },
    // 排序历史
    initOrder() {
      let order = localStorage.getItem('order')
      let desc = localStorage.getItem('desc')
      if (order) {
        this.queryParam.order = order
      } else {
        // 设置localStorage
        localStorage.setItem('order', this.queryParam.order)
      }

      if (desc) {
        this.queryParam.desc = desc
      } else {
        // 设置localStorage
        localStorage.setItem('desc', this.queryParam.desc)
      }
    },
    // 排序
    changeOrder(order) {
      if (this.queryParam.order === order) {
        // 点击的是同一个分类，修改降序和升序
        if (parseInt(this.queryParam.desc) === 1) {
          this.queryParam.desc = 0
          localStorage.setItem('desc', 0)
        } else {
          this.queryParam.desc = 1
          localStorage.setItem('desc', 1)
        }
      } else {
        this.queryParam.order = order
        localStorage.setItem('order', order)
      }
      // 重新查询数据
      this.resetQueryParam()
      this.getList()
    },
    // 打开目录
    openDir(dir) {
      this.isSearch = 0
      this.queryParam.dir = dir
      this.queryParam.page = 1
      this.checkedList = []
      this.isIndeterminate = false
      this.assembleDir(dir)
      this.getList()
    },
    // 封装面包屑导航路径
    assembleDir(dir) {
      this.breadcrumb = generateBreadcrumb(dir, 3)
    },
    // 点击非复选框区域单选
    checkOne(obj) {
      if (this.checkedList.indexOf(obj.id) === -1) {
        this.checkedList = [obj.id]
        if (obj.isDir === 1) {
          this.checkList.folderIds = [obj.id]
          this.checkList.fileIds = []
        } else {
          this.checkList.fileIds = [obj.id]
          this.checkList.folderIds = []
        }
        this.checkedFileName = obj.name
      } else {
        this.checkedList = []
        this.checkList.folderIds = []
        this.checkList.fileIds = []
        this.checkedFileName = ''
      }
    },
    // 点击复选框多选
    checkMore(checked, obj) {
      if (checked) {
        if (obj.isDir === 1) {
          this.checkList.folderIds.push(obj.id)
        } else {
          this.checkList.fileIds.push(obj.id)
        }
        this.checkedFileName = obj.name
      } else {
        if (obj.isDir === 1) {
          this.checkList.folderIds.splice(
            this.checkList.folderIds.indexOf(obj.id),
            1
          )
        } else {
          this.checkList.fileIds.splice(
            this.checkList.folderIds.indexOf(obj.id),
            1
          )
        }
        if (this.checkedList.length === 1) {
          this.list.forEach((item) => {
            if (item.id === this.checkedList[0]) {
              this.checkedFileName = item.name
              return
            }
          })
        }
      }
    },
    // 全选反选
    handleCheckAllChange(val) {
      if (val) {
        // 全选
        this.checkedList = []
        this.checkList.folderIds = []
        this.checkList.fileIds = []
        for (let index = 0; index < this.list.length; index++) {
          const element = this.list[index]
          this.checkedList.push(element.id)
          if (element.isDir === 1) {
            this.checkList.folderIds.push(element.id)
          } else {
            this.checkList.fileIds.push(element.id)
          }
        }
      } else {
        // 取消全选
        this.checkedList = []
        this.checkList.folderIds = []
        this.checkList.fileIds = []
      }
    },
    // 获取列表数据
    getList(isScroll = false) {
      this.startLoading()
      let param
      if (this.isSearch == 0) {
        param = this.queryParam
        param.isSearch = 0
      } else {
        param = this.searchParam
        param.isSearch = 1
      }
      this.getRequest('/portal/list/', param).then((resp) => {
        this.closeLoading()
        if (isScroll) {
          this.list.push(...resp.data.list)
        } else {
          this.list = resp.data.list
        }

        this.count = resp.data.count
        this.isAll = resp.data.isAll
      })
    },
    // 禁止时间冒泡
    stopDefault(e) {
      e.stopPropagation()
    },
    // 自适应文件列表高度
    transferHeight() {
      this.content.height = window.innerHeight - 178 + 'px'
      // 刷新滚动条
      this.$nextTick(() => {
        if (this.$refs['vs']) {
          this.$refs['vs'].refresh()
        }
      })
    },
    // 滚动加载数据
    handleScroll(vertical) {
      if (vertical.process === 1 && this.isAll === 0) {
        this.queryParam.page += 1
        this.getList(true)
        this.$nextTick(() => {
          if (this.$refs['vs']) {
            this.$refs['vs'].refresh()
          }
        })
      }
    },
    paixuMouseenter() {
      this.paixuUl = true
      this.mouseInDiv = true
    },
    paixuMouseleave() {
      this.mouseInDiv = false
      setTimeout(() => {
        if (!this.mouseInUl) {
          this.paixuUl = false
        }
      }, 500)
    },
    paixuUlMouseenter() {
      this.mouseInUl = true
    },
    paixuUlMouseleave() {
      this.mouseInUl = false
      setTimeout(() => {
        if (!this.mouseInDiv) {
          this.paixuUl = false
        }
      }, 500)
    },
    changePaixu(order) {
      this.changeOrder(order)
      this.mouseInUl = false
      this.paixuUl = false
      this.mouseInDiv = false
    },
  },
  watch: {
    checkedList: {
      handler() {
        if (this.checkedList.length > 0) {
          this.btnGroup.show = true
          if (this.checkedList.length > 1) {
            this.btnGroup.disabled = true
          } else {
            this.btnGroup.disabled = false
          }

          if (this.checkedList.length < this.count) {
            this.checkAll = false
            this.isIndeterminate = true
          } else {
            this.isIndeterminate = false
            this.checkAll = true
          }
        } else {
          this.btnGroup.show = false
          this.btnGroup.disabled = false
          this.isIndeterminate = false
          this.checkAll = false
        }
      },
      deep: true,
    },
  },
  filters: {
    breadcrumbFilter: function(val, deep) {
      if (!val) return ''
      if (deep === 1) {
        if (val.length > 30) {
          return val.substring(0, 30) + '...'
        }
        return val
      }

      if (deep === 2) {
        if (val.length > 20) {
          return val.substring(0, 10) + '...'
        }
        return val
      }

      if (deep === 3) {
        if (val.length > 15) {
          return val.substring(0, 8) + '...'
        }
        return val
      }

      if (deep > 3) {
        if (val.length > 10) {
          return val.substring(0, 5) + '...'
        }
        return val
      }
    },
    directoryFilter: function(val) {
      if (val.length > 15) {
        return val.substring(0, 15) + '...'
      }

      return val
    },
  },
}
</script>

<style lang="less">
.el-button [class*='iconfont'] + span {
  margin-left: 5px;
}

.el-checkbox__inner {
  width: 16px;
  height: 16px;
  border-radius: 4px;
  &::after {
    left: 5px;
    top: 2px;
  }
  &::before {
    top: 6px !important;
  }
}

.file-fun-area {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 3px 0;

  .fun-btn {
    padding: 11px 10px;

    .btn-group {
      // margin-top: -1px;
      margin-left: 10px;

      .el-button--primary:first-child {
        border-right-color: #b3d8ff;
      }

      .el-button--primary:not(:first-child):not(:last-child) {
        border-left-color: #b3d8ff;
        border-right-color: #b3d8ff;
      }

      .el-button--primary:last-child {
        border-left-color: #b3d8ff;
      }

      .iconfont {
        font-size: 12px;
      }
    }

    .plain-btn.is-plain,
    .uploaderBtn {
      font-weight: 700;
    }
  }

  .right-fun {
    width: 30%;

    .fun-search {
      float: right;
      width: 80%;
      min-width: 165px;
      max-width: 315px;
      padding-left: 15px;
      padding-right: 13px;
      background: #f1f2f4;
      border-radius: 33px;
      display: flex;
      align-items: center;
      margin-right: 8px;

      .search-input {
        input {
          background: 0 0;
          border: none;
          padding: 0;
        }
      }

      .search-icon {
        font-weight: 700;
        cursor: pointer;
        color: #687176;

        &:hover {
          color: #409eff;
        }
      }
    }

    .icon-fun-area {
      float: right;
      display: flex;
      height: 30px;
      align-items: center;
      .paixu-i {
        &:hover {
          color: #7f8da9;
        }
      }
      .paixu-div {
        position: relative;
        .paixu-ul {
          width: 100px;
          height: auto;
          position: absolute;
          border: 1px solid #409eff;
          right: 0;
          background-color: #fff;
          border-radius: 4px;
          z-index: 9999;
          .paixu-li {
            padding: 5px 10px;
            color: #409eff;
            user-select: none;
            cursor: pointer;
            text-align: left;
            font-size: 12px;
            line-height: 1.5;
            &:first-child {
              border-top-left-radius: 4px;
              border-top-right-radius: 4px;
            }
            &:last-child {
              border-bottom-left-radius: 4px;
              border-bottom-right-radius: 4px;
            }
            &:hover {
              background: #f6faff;
            }
            span {
              display: block;
              text-indent: 23px;
              position: relative;
              text-align: left;
              em {
                font-weight: 700;
                position: absolute;
                left: 2px;
                top: 4px;
                text-indent: 0;
              }
            }
          }
        }
      }

      .iconfont {
        cursor: pointer;
        color: #687176;
      }

      .icon-paixu {
        font-size: 24px;
        line-height: 30px;
        margin-right: 10px;
      }

      .icon-viewgallery,
      .icon-viewlist {
        font-size: 18px;
        margin-right: 20px;
        font-weight: 600;
      }
    }
  }
}

.file-container {
  .title-info {
    padding-left: 16px;
    height: 16px;
    line-height: 16px;
    margin-bottom: 5px;

    span {
      font-size: 12px;

      &.textSpan {
        color: #666;
      }
    }

    .menu-tag {
      float: left;

      .breadcrumbUl {
        li {
          float: left;
        }

        .breadcrumbTxtSpan {
          font-size: 12px;
          vertical-align: middle;
          color: #666;
        }
      }
    }

    .load-count {
      float: right;
      margin-right: 20px;
    }
  }

  .file-list {
    .list-title {
      .title-ul {
        height: 36px;
        line-height: 36px;
        color: #888;
        border-bottom: 1px solid rgba(179, 216, 255, 0.2);

        li {
          float: left;
          cursor: pointer;
          font-size: 12px;
          padding-left: 10px;
          user-select: none;

          &:hover {
            background-color: rgba(179, 216, 255, 0.2);
          }
        }

        .sortIcon {
          font-weight: 700;
          color: #409eff;
          margin-left: 5px;
        }
      }
    }

    .list-content {
      height: auto;
      font: 12px/1.5 tahoma, arial !important;

      .content-row {
        height: 44px;
        line-height: 44px;
        border-bottom: 1px solid rgba(179, 216, 255, 0.2);
        white-space: nowrap;
        text-overflow: ellipsis;
        position: relative;
        cursor: pointer;

        &.first:hover::before {
          top: 0;
        }

        &:hover {
          background: rgba(179, 216, 255, 0.2);
          border-bottom: 1px solid rgba(179, 216, 255, 0.6);

          &::before {
            content: '';
            border-top: 1px solid rgba(179, 216, 255, 0.6);
            position: absolute;
            top: -1px;
            display: block;
            width: 100%;
            z-index: 1;
            visibility: visible;
          }
        }

        .left {
          float: left;
        }

        .content-flex {
          display: flex;
          align-items: center;
        }

        .content-col {
          height: 44px;
          line-height: 44px;
          padding-left: 10px;
          font-family: tahoma, arial !important;
          margin-top: -1px;

          .txtSpan {
            color: #424e67;
            text-decoration: none;
            display: inline-block;
            cursor: default;

            a {
              color: #424e67;

              &:hover {
                color: #09aaff;
              }
            }

            .dirLink {
              text-decoration: underline;
            }
          }

          img {
            width: 25px;
            height: 25px;
            cursor: default;
          }

          .el-checkbox {
            margin-top: 4px;
          }
        }
      }
    }
  }
}
</style>

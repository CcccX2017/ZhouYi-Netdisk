<template>
  <el-dialog
      :title="title"
      :visible.sync="visible"
      custom-class="dialog-radius no-padding"
      width="720px"
      :before-close="beforeClose"
      ref="copyOrMoveDialog"
  >
    <template v-slot:title>
      <div style="display: flex; align-items: center">
        <span style="font-weight: 700; font-size: 14px;">{{ title }}</span>
        <div style="display: flex; align-items: center; font-size: 12px;">
          <div style="margin: 0 8px 0 24px;">
            <img :src="$store.getters.avatar" alt="" style="width: 24px; height: 24px; vertical-align: middle;
        line-height: 24px; border-radius: 50%">
          </div>
          <div style="font-weight: 700;">
            <h5 style="color: #03081a; max-width: 200px; overflow: hidden;text-overflow: ellipsis; white-space: nowrap;"
                :title="$store.getters.nickname || $store.getters.name">
              {{ $store.getters.nickname || $store.getters.name }}
            </h5>
          </div>
        </div>
      </div>
    </template>
    <div class="name-nav" v-if="breadcrumbs.length === 0">{{ $store.getters.nickname || $store.getters.name }}</div>
    <div class="name-nav" v-else>
      <ul
          class="breadcrumbUl"
      >
        <li>
          <el-link type="primary" @click.native="goBack(null)">
            返回上一级
          </el-link>
          <span  class="breadcrumbSymbolSpan">&#124;</span>
        </li>
        <li>
          <el-link
              type="primary"
              @click.native="goBack('/')"
              :title="$store.getters.nickname || $store.getters.name"
          >
            {{ $store.getters.nickname || $store.getters.name }}
          </el-link>
          <span class="breadcrumbSymbolSpan">&gt;</span>
          <template v-for="(item, index) in breadcrumbs">
            <el-link
                type="primary"
                @click.native="goBack(item.path)"
                :title="item.name"
                v-if="index < breadcrumbs.length - 1"
            >
              {{ item.name | breadcrumbFilter(breadcrumbs.length) }}
            </el-link>
            <span class="breadcrumbTxtSpan" v-else :title="item.name">
              {{ item.name | breadcrumbFilter(breadcrumbs.length) }}
            </span>
            <span class="breadcrumbSymbolSpan" v-if="index < breadcrumbs.length - 1">&gt;</span>
          </template>
        </li>
      </ul>
    </div>
    <div class="content" v-if="visible">
      <vuescroll
          v-loading="loading"
          element-loading-text="加载中"
          element-loading-background="#fff"
      >
        <el-table
            :data="dirs"
            style="width: 100%;"
            :show-header="false"
            v-if="!loading && dirs.length > 0"
        >
          <el-table-column
              label="文件名"
          >
            <template v-slot="{row}">
              <div class="dir-item" @click="handleClickDir(row.folderName, row.dir)">
                <img src="@/assets/filetype/folder.png" alt=""
                     style="width: 29px; height: 29px; vertical-align: middle">
                <el-link class="dir-link" :underline="false" :key="row.folderId"
                         @click.stop="handleClickDir(row.folderName, row.dir)">
                  {{ row.folderName }}
                </el-link>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <div v-else-if="!loading && dirs.length <= 0" class="empty-dirs">
          <div style="padding: 90px; text-align: center; cursor: default">
            <img src="@/assets/filetype/empty-folder.png" alt="">
            <p style="font-size: 14px; color: #afb3bf; line-height: 18px; margin-top: 8px;" :title="currentDir">
              {{ title + ' ' + currentDir | directoryFilter }} 文件夹
            </p>
          </div>
        </div>
      </vuescroll>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" plain round @click="beforeClose">取 消</el-button>
      <el-button type="primary" round>{{ title }}此</el-button>
    </span>
  </el-dialog>
</template>

<script>
import vuescroll from "vuescroll";
import {breadcrumbFilter, directoryFilter, generateBreadcrumb} from "@/utils/breadcrumb";

export default {
  name: "CopyOrMove",
  components: {
    vuescroll
  },
  props: {
    title: {
      type: String,
      default: ''
    },
    visible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      dirs: [],
      // 当前文件夹名称
      currentDir: '',
      // 目录路径
      dirPath: '/',
      loading: true,
      // 面包屑导航数组
      breadcrumbs: [],
    }
  },
  mounted() {
    this.getDirListByParentDir('/')
  },
  methods: {
    // 返回上一级
    goBack(path) {
      if (path) {
        this.dirPath = path
      } else {
        // 返回上一级
        let index = this.dirPath.lastIndexOf('/')
        if (index === 0) {
          this.dirPath = '/'
        } else {
          this.dirPath = this.dirPath.substring(0, index)
        }
      }
      this.breadcrumbs = generateBreadcrumb(this.dirPath, 3)
      this.loading = true
      this.getDirListByParentDir(this.dirPath)
    },
    // 点击文件夹名称
    handleClickDir(name, path) {
      this.currentDir = name;
      this.loading = true;
      this.dirPath = this.getDirPath(path, name)
      this.breadcrumbs = generateBreadcrumb(this.dirPath, 3)
      this.getDirListByParentDir(this.getDirPath(path, name))
    },
    // 根据文件夹名称获取完整路径
    getDirPath(prefixPath, folderName) {
      return prefixPath === '/' ? `/${folderName}` : `${prefixPath}/${folderName}`
    },
    // 获取指定目录下的文件夹列表
    getDirListByParentDir(parentDir) {
      this.getRequest(`/portal/folders/?dir=${parentDir}`).then(resp => {
        this.loading = false
        this.dirs = resp.data;
      })
    },
    beforeClose() {
      this.$emit("update:visible", false)
    }
  },
  filters: {
    breadcrumbFilter: breadcrumbFilter,
    directoryFilter: directoryFilter
  }
}
</script>

<style lang="less" scoped>
.name-nav {
  position: absolute;
  width: 100%;
  height: 40px;
  line-height: 40px;
  left: 0;
  top: 54px;
  background-color: #fafafc;
  color: #afb3bf;
  border-radius: 4px 4px 0 0;
  border-top: 1px solid #f5f6fa;
  padding: 0 14px 0 24px;
  font-size: 12px;
  box-sizing: border-box;

  .breadcrumbUl {
    li {
      float: left;
    }

    .breadcrumbSymbolSpan,
    .breadcrumbTxtSpan {
      display: inline-block;
      vertical-align: middle;
      font-size: 12px;

      &.breadcrumbSymbolSpan {
        margin: 0 4px;
        color: #c4d8f4;
      }

      &.breadcrumbTxtSpan {
        color: #afb3bf;
      }
    }

    /deep/ .el-link--inner {
      font-size: 12px;
    }
  }
}

.content {
  height: 300px;
  margin-top: 40px;

  .empty-dirs {
    width: 100%;
    height: 300px;
    display: flex;
    justify-content: center;
    align-items: center;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
  }

  .dir-item {
    padding-left: 20px;
    user-select: none;
    cursor: pointer;

    .dir-link {
      display: inline-block;
      max-width: calc(100% - 50px);
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
      margin-left: 15px;
    }
  }
}

</style>
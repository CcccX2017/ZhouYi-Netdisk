<template>
  <el-dialog
      :title="title"
      :visible.sync="visible"
      custom-class="dialog-radius no-padding"
      width="720px">
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
    <div class="name-nav">{{ $store.getters.nickname || $store.getters.name }}</div>
    <div class="content">
      <vuescroll>
        <el-table
            :data="dirs"
            style="width: 100%"
            :show-header="false"
        >
          <el-table-column
              label="文件名"
          >
            <template v-slot="{row}">
              <div style="padding-left: 20px; user-select: none">
                <img src="@/assets/filetype/folder.png" alt=""
                     style="width: 29px; height: 29px; vertical-align: middle">
                <el-link class="dir-link" :underline="false">{{ row.folderName }}</el-link>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </vuescroll>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" plain round>取 消</el-button>
      <el-button type="primary" round>{{ title }}此</el-button>
    </span>
  </el-dialog>
</template>

<script>
import vuescroll from "vuescroll";

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
      dirs: []
    }
  },
  created() {
    this.getDirListByParentDir('/')
  },
  methods: {
    getDirListByParentDir(parentDir) {
      this.getRequest(`/portal/folders/?dir=${parentDir}`).then(resp => {
        this.dirs = resp.data;
      })
    }
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
}

.content {
  height: 300px;
  margin-top: 40px;

  .dir-link {
    display: inline-block;
    max-width: calc(100% - 50px);
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    margin-left: 15px;
  }
}

</style>
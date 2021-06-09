<template>
  <!-- 新建文件夹 -->
  <el-dialog
    title="新建文件夹"
    :visible.sync="visible"
    width="350px"
    custom-class="folderDialog"
    :close-on-click-modal="false"
    :before-close="beforeClose"
    @opened="opened"
  >
    <el-form :model="folderForm" :rules="folderRules" ref="folderForm">
      <el-form-item prop="folderName">
        <el-input
          v-model="folderForm.folderName"
          placeholder="请输入文件夹名称"
          @keyup.enter.native="createFolder"
          ref="folderInput"
        ></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="beforeClose">取 消</el-button>
      <el-button type="primary" @click="createFolder">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      folderForm: {
        folderName: '',
      },
      folderRules: {
        folderName: [
          { required: true, message: '请输入文件(夹)名称', trigger: 'blur' },
          {
            min: 1,
            max: 255,
            message: '文件(夹)名称不能超过255个字节',
            trigger: 'blur',
          },
        ],
      },
    }
  },
  props: {
    visible: {
      type: Boolean,
      default: false,
    },
    dir: {
      type: String,
      default: '',
    },
  },
  methods: {
    opened() {
      if (this.$refs.folderForm) {
        this.$refs.folderForm.resetFields()
      }
      this.$nextTick(() => {
        if (this.$refs.folderInput.$el) {
          this.$refs.folderInput.$el.children[0].focus()
        }
      })
    },
    beforeClose() {
      this.folderForm.folderName = ''
      this.$emit('update:visible', false)
    },
    // 新建文件夹
    createFolder() {
      this.$refs.folderForm.validate((valid) => {
        if (valid) {
          let param = {
            folderName: this.folderForm.folderName,
            dir: this.dir,
          }
          // 发送新增文件夹请求
          this.postRequest('/portal/folders/', param).then((resp) => {
            if (resp) {
              this.$emit('resetQueryParam', false)
              this.$emit('getList', false)
              this.beforeClose()
            }
          })
        }
      })
    },
  },
}
</script>

<style lang="less">
.folderDialog {
  .el-dialog__body {
    padding-top: 10px;
    padding-bottom: 0;
  }

  .el-dialog__footer {
    padding-top: 0;
    padding-bottom: 15px;
  }
}
</style>

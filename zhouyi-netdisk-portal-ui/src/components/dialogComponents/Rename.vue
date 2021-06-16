<template>
  <el-dialog
    title="重命名"
    :visible.sync="visible"
    width="350px"
    custom-class="folderDialog"
    :close-on-click-modal="false"
    :before-close="beforeClose"
    @opened="opened"
  >
    <el-form :model="renameForm" :rules="renameRules" ref="renameForm">
      <el-form-item prop="fileName">
        <el-input
          v-model="renameForm.fileName"
          placeholder="请输入文件(夹)名称"
          @keyup.enter.native="rename"
          ref="renameInput"
        ></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="beforeClose">取 消</el-button>
      <el-button type="primary" @click="rename">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      renameForm: {
        fileName: '',
      },
      renameRules: {
        fileName: [
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
    checkedFileName: {
      type: String,
      default: '',
    },
    checkList: {
      type: Object,
      default: null,
    },
    dir: {
      type: String,
      default: '',
    }
  },
  mounted() {},
  methods: {
    opened() {
      if (this.$refs.renameForm) {
        this.$refs.renameForm.resetFields()
      }
      this.renameForm.fileName = this.checkedFileName
      this.$nextTick(() => {
        let el = this.$refs.renameInput.$el.children[0]
        el.select()
        el.selectionEnd = this.renameForm.fileName.lastIndexOf('.')
      })
    },
    beforeClose() {
      this.renameForm.fileName = ''
      this.$emit('update:visible', false)
    },
    rename() {
      this.$refs.renameForm.validate((valid) => {
        if (valid) {
          let param = {
            newName: this.renameForm.fileName,
            dir: this.dir,
          }

          let id, isDir

          let fileIds = this.checkList.fileIds
          let folderIds = this.checkList.folderIds
          if (fileIds.length > 0) {
            id = fileIds[0]
            isDir = 0
          } else if (folderIds.length > 0) {
            id = folderIds[0]
            isDir = 1
          } else {
            return
          }
          let msg = this.$message({
            message: '正在重命名文件，请稍后...',
            iconClass: 'el-icon-loading',
            duration: 0,
          })
          // 发送重命名请求
          this.putRequest(`/portal/list/${id}/${isDir}`, param).then((resp) => {
            msg.close()
            if (resp) {
              if (resp.status !== 20) {
                this.$emit('resetCheckedList', false)
                this.$emit('resetQueryParam', false)
                this.$emit('getList', false)
                this.beforeClose()
              } else {
                this.$confirm(`${resp.msg}`, '提示', {
                  confirmButtonText: '保留两个文件',
                  cancelButtonText: '取消',
                  type: 'warning',
                })
                  .then(() => {
                    msg = this.$message({
                      message: '正在重命名文件，请稍后...',
                      iconClass: 'el-icon-loading',
                      duration: 0,
                    })
                    param.type = 'newCopy'
                    this.putRequest(`/portal/list/${id}/${isDir}`, param).then(
                      (resp) => {
                        if (resp) {
                          msg.close()
                          this.$emit('resetCheckedList', false)
                          this.$emit('resetQueryParam', false)
                          this.$emit('getList', false)
                          this.beforeClose()
                        }
                      }
                    )
                  })
                  .catch(() => {})
              }
            }
          })
        }
      })
    },
  },
}
</script>

<style lang="less"></style>

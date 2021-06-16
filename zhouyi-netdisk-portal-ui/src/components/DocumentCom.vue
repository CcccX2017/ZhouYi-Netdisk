<template>
  <div>文档{{ vmode }}</div>
</template>

<script>
export default {
  data() {
    return {
      vmode: '',
    }
  },
  mounted() {
    this.vmode = this.$route.query.vmode
    //console.log(this.$route)
  },
  methods: {},
}
</script>

<style></style>

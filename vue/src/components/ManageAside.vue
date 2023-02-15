<template>
  <div class="aside">
    <el-menu  style="min-height: calc(100vh - 60px)"
        :default-active="this.$router.path"
        class="el-menu-vertical-demo"
        background-color="#a0a0a0"
        text-color="#ffffff"
        active-text-color="#ffd04b"
        router>
        <el-menu-item-group>
          <el-menu-item index="/manageBook">图书管理</el-menu-item>
          <el-menu-item index="/manageUser">用户管理</el-menu-item>
        </el-menu-item-group>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "ManageAside",

}
</script>

<style scoped>
.el-aside {
  line-height: 100px;
}
</style>
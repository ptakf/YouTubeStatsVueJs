<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <nav data-bs-theme="dark" class="navbar bg-primary navbar-expand-lg shadow p-2">
    <div class="container-fluid">
      <RouterLink to="/" class="navbar-brand text-light d-flex align-items-center">
        <img src="/favicon.ico" alt="logo" width="50" height="50" />
        <span class="ms-1">YouTubeStats</span>
      </RouterLink>

      <div class="navbar-nav d-flex align-items-center justify-content-center">
        <RouterLink to="/" class="nav-link">Home</RouterLink>
        <RouterLink to="/channel/add" class="nav-link">Add Channels</RouterLink>
        <RouterLink to="/channel/list" class="nav-link">Channel List</RouterLink>
        <RouterLink to="/about" class="nav-link">About</RouterLink>
      </div>
    </div>
  </nav>

  <div class="container bg-light d-flex flex-column min-vh-100 shadow">
    <div class="my-4">
      <RouterView />
    </div>
  </div>
</template>

<style lang="scss">
@import './assets/styles.scss';
</style>

<template>
    <Navbar />
    <div>
        <el-table :data="msgLists">
            <el-table-column prop="msgId" label="消息ID"></el-table-column>
            <el-table-column prop="userId" label="用户ID"></el-table-column>
            <el-table-column prop="content" label="内容"></el-table-column>
            <el-table-column prop="time" label="时间" :formatter="formatTime"></el-table-column>
        </el-table>
    </div>
</template>
    
<script>
import axios from 'axios';
axios.defaults.baseURL = 'http://localhost:8080';
import Navbar from '../components/UserNav.vue';

export default {
    components: {
        Navbar,
    },
    data() {
        return {
            msgLists: [],
        };
    },
    mounted() {
        this.fetchMsgLists();

    },
    methods: {
        async fetchMsgLists() {
            const userId = localStorage.getItem("user_id");
            const response = await axios.get(`/messages/${userId}`);
            this.msgLists = response.data;
            console.log(response)
            console.log(this.msgLists)
        },
        formatTime(row, column, cellValue) {
            const date = new Date(cellValue);
            date.setHours(date.getHours() - 8); // 向后调整 8 小时
            return date.toLocaleString(); // 格式化日期和时间
        }

    },
};
</script>
    
    
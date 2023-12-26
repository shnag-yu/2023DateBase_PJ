<template>
    <Navbar />

    <div v-if="showAddPlatformDialog" class="edit-dialog-overlay">
        <div class="edit-dialog-container">
            <el-form ref="registerForm" :model="registerForm" label-width="80px" class="register-form" :rules="rules">
                <!-- Platformname Field -->
                <el-form-item label="用户名" prop="name">
                    <el-input v-model="registerForm.name" placeholder="请输入用户名" prefix-icon="el-icon-platform"></el-input>
                </el-form-item>
                <!-- Register Button -->
                <div slot="footer" class="dialog-footer">
                    <el-button @click="showAddPlatformDialog = false">返回</el-button>
                    <el-button type="primary" @click="submitReg(registerForm)">新增平台</el-button>
                </div>
            </el-form>
        </div>
    </div>
    <div class="platform-management">
        <el-table :data="pagedPlatforms">
            <el-table-column prop="id" label="ID"></el-table-column>
            <el-table-column prop="name" label="名称"></el-table-column>
            <el-table-column label="操作">
                <template #default="{ row }">
                    <el-button type="primary" size="small" @click="editPlatform(row)">修改</el-button>
                </template>
            </el-table-column>

        </el-table>
        <div class="pagination-container">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
                :page-size="pageSize" :total="platforms.length" layout="total, sizes, prev, pager, next, jumper">
            </el-pagination>
        </div>

        <div v-if="editDialogVisible" class="edit-dialog-overlay">
            <!-- 用户编辑表单 -->
            <div class="edit-dialog-container">
                <el-form ref="currentEditPlatform" :model="currentEditPlatform" label-width="80px" class="register-form"
                    :rules="rules">
                    <!-- Platformname Field -->
                    <el-form-item label="平台名" prop="name">
                        <el-input v-model="currentEditPlatform.name" placeholder="请输入平台" prefix-icon="el-icon-platform"></el-input>
                    </el-form-item>

                    <!-- Register Button -->
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="editDialogVisible = false">返回</el-button>
                        <el-button type="primary" @click="submitEdit(currentEditPlatform)">提交</el-button>
                    </div>
                </el-form>
            </div>
        </div>
    </div>
</template>
  
<script>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import Navbar from '../components/UserNav.vue';
axios.defaults.baseURL = 'http://localhost:8080';
export default {
    components: {
        Navbar,
    },
    data() {
        return {
            platforms: [],
            editDialogVisible: false,
            showAddPlatformDialog: false,
            currentEditPlatform: {},
            pagedPlatforms: [], // 存储当前页的用户数据
            currentPage: 1,
            pageSize: 10,
            registerForm: {
                name: '',
                password: '',
                age: '',
                gender: '',
                tel: '',
            },
            rules: {
                name: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                    { max: 32, message: '平台不能超过32字符', trigger: 'blur' },
                ],
            },
        };
    },
    methods: {
        async fetchPlatforms() {
            const res = await axios.get('/platforms');
            this.platforms = res.data;
            console.log(this.platforms);;
            this.updatePagedPlatforms();
        },
        editPlatform(platform) {
            this.currentEditPlatform = { ...platform };
            this.editDialogVisible = true;
            console.log(this.currentEditPlatform);
            console.log(this.editDialogVisible);
        },
        submitEdit(platform) {
            const id = platform.id;
            axios.put(`/platforms/${id}`, platform)
                .then(() => {
                    this.fetchPlatforms(); // 重新获取用户列表
                    this.$message.success('修改用户成功')
                })
                .catch(error => {
                    console.error('修改用户失败:', error);
                });
            // 发送修改用户的请求
            // 更新成功后关闭对话框并刷新用户列表
            this.editDialogVisible = false;
            this.fetchPlatforms();
        },
        updatePagedPlatforms() {
            const startIndex = (this.currentPage - 1) * this.pageSize;
            const endIndex = startIndex + this.pageSize;
            this.pagedPlatforms = this.platforms.slice(startIndex, endIndex);
            console.log(this.pagedPlatforms);
        },
        handleSizeChange(newSize) {
            this.pageSize = newSize;
            this.updatePagedPlatforms();
        },
        handleCurrentChange(newPage) {
            this.currentPage = newPage;
            this.updatePagedPlatforms();
        },
        submitReg(registerForm) {
            this.$refs.registerForm.validate((valid) => {
                if (valid) {
                    axios.post('/auth/register', this.registerForm)
                        .then((res) => {
                            console.log(res.data)
                            if (res.data.code === 200) {
                                this.$message.success('新增用户成功');
                                this.showAddPlatformDialog = false;
                                this.fetchPlatforms();
                            } else {
                                this.$message.error(res.data.msg);
                            }
                        })
                        .catch((err) => {
                            console.log(err)
                            this.$message.error('注册失败，请检查信息是否符合要求');
                        });
                } else {
                    this.$message.error('请检查表单信息是否填写正确');
                }
            });

        }
    },
    mounted() {
        this.fetchPlatforms();
    }
};
</script>
  
<style>
.edit-dialog-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
    /* 确保遮罩层在其他内容之上 */
}

.edit-dialog-container {
    background-color: white;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
}

.dialog-footer {
    display: flex;
    justify-content: space-around;
    margin-top: 20px;
}

.platform-management .pagination-container {
    display: flex;
    justify-content: center;
    margin-top: 50px;
    /* 调整与表格的距离 */
    margin-bottom: 50px;
}

.merchant-info {
    text-align: center;
    /* 居中对齐 */
    font-size: larger;
    /* 字体放大 */
    background-color: #cbdae9;
    /* 背景为灰色 */
    margin: 20px auto;
    /* 与上下内容保持一定距离 */
    padding: 20px;
    /* 内部填充 */
    border-radius: 10px;
    /* 圆角边框 */
}
.platform-management{
    margin-left: 50px;
    margin-right: 50px;
    margin-top: 50px;
}
</style>
  
<template>
    <Navbar />

    <div class="merchant-info">
        <el-button @click="showAddUserDialog = true" type="success" size="large">新增用户</el-button>
    </div>

    <div v-if="showAddUserDialog" class="edit-dialog-overlay">
        <div class="edit-dialog-container">
            <el-form ref="registerForm" :model="registerForm" label-width="80px" class="register-form" :rules="rules">
                <!-- Username Field -->
                <el-form-item label="用户名" prop="name">
                    <el-input v-model="registerForm.name" placeholder="请输入用户名" prefix-icon="el-icon-user"></el-input>
                </el-form-item>

                <!-- Password Field -->
                <el-form-item label="密码" prop="password">
                    <el-input v-model="registerForm.password" type="password" placeholder="请输入密码"
                        prefix-icon="el-icon-lock"></el-input>
                </el-form-item>

                <!-- Age Field -->
                <el-form-item label="年龄" prop="age">
                    <el-input v-model="registerForm.age" placeholder="请输入年龄" prefix-icon="el-icon-time"></el-input>
                </el-form-item>

                <!-- Gender Field -->
                <el-form-item label="性别" prop="gender">
                    <el-select v-model="registerForm.gender" placeholder="请选择性别">
                        <el-option label="男" value="Male"></el-option>
                        <el-option label="女" value="Female"></el-option>
                    </el-select>
                </el-form-item>

                <!-- Tel Field -->
                <el-form-item label="电话" prop="tel">
                    <el-input v-model="registerForm.tel" placeholder="请输入电话" prefix-icon="el-icon-phone"></el-input>
                </el-form-item>

                <!-- Register Button -->
                <div slot="footer" class="dialog-footer">
                    <el-button @click="showAddUserDialog = false">返回</el-button>
                    <el-button type="primary" @click="submitReg(registerForm)">新增用户</el-button>
                </div>
            </el-form>
        </div>
    </div>
    <div class="user-management">
        <el-table :data="pagedUsers">
            <el-table-column prop="id" label="ID"></el-table-column>
            <el-table-column prop="name" label="姓名"></el-table-column>
            <el-table-column prop="password" label="密码"></el-table-column>
            <el-table-column prop="age" label="年龄"></el-table-column>
            <el-table-column prop="gender" label="性别"></el-table-column>
            <el-table-column prop="tel" label="电话"></el-table-column>
            <el-table-column label="操作">
                <template #default="{ row }">
                    <el-button size="small" @click="editUser(row)">修改</el-button>
                    <el-button size="small" type="danger" @click="deleteUser(row.id)">删除</el-button>
                </template>
            </el-table-column>

        </el-table>
        <div class="pagination-container">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
                :page-size="pageSize" :total="users.length" layout="total, sizes, prev, pager, next, jumper">
            </el-pagination>
        </div>

        <div v-if="editDialogVisible" class="edit-dialog-overlay">
            <!-- 用户编辑表单 -->
            <div class="edit-dialog-container">
                <el-form ref="currentEditUser" :model="currentEditUser" label-width="80px" class="register-form"
                    :rules="rules">
                    <!-- Username Field -->
                    <el-form-item label="用户名" prop="name">
                        <el-input v-model="currentEditUser.name" placeholder="请输入用户名" prefix-icon="el-icon-user"></el-input>
                    </el-form-item>

                    <!-- Password Field -->
                    <el-form-item label="密码" prop="password">
                        <el-input v-model="currentEditUser.password" placeholder="请输入密码"
                            prefix-icon="el-icon-lock"></el-input>
                    </el-form-item>

                    <!-- Age Field -->
                    <el-form-item label="年龄" prop="age">
                        <el-input v-model="currentEditUser.age" placeholder="请输入年龄" prefix-icon="el-icon-time"></el-input>
                    </el-form-item>

                    <!-- Gender Field -->
                    <el-form-item label="性别" prop="gender">
                        <el-select v-model="currentEditUser.gender" placeholder="请选择性别">
                            <el-option label="男" value="Male"></el-option>
                            <el-option label="女" value="Female"></el-option>
                        </el-select>
                    </el-form-item>

                    <!-- Tel Field -->
                    <el-form-item label="电话" prop="tel">
                        <el-input v-model="currentEditUser.tel" placeholder="请输入电话" prefix-icon="el-icon-phone"></el-input>
                    </el-form-item>

                    <!-- Register Button -->
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="editDialogVisible = false">返回</el-button>
                        <el-button type="primary" @click="submitEdit(currentEditUser)">提交</el-button>
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
            users: [],
            editDialogVisible: false,
            showAddUserDialog: false,
            currentEditUser: {},
            pagedUsers: [], // 存储当前页的用户数据
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
                    { max: 32, message: '用户名不能超过32字符', trigger: 'blur' },
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { min: 8, message: '密码不少于8位', trigger: 'blur' },
                ],
                age: [
                    { required: true, message: '请输入年龄', trigger: 'blur' },
                    { max: 3, message: '年龄过大', trigger: 'blur' },
                    { pattern: /^\d+$/, message: '年龄只能是数字', trigger: 'blur' },
                ],

                gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
                tel: [
                    { required: true, message: '请输入电话', trigger: 'blur' },
                    { max: 11, message: '电话号码不超过11位', trigger: 'blur' },
                    { pattern: /^\d+$/, message: '电话号码只能是数字', trigger: 'blur' },
                ],
            },
        };
    },
    methods: {
        fetchUsers() {
            axios.get('/user')
                .then(response => {
                    this.users = response.data.data;
                    this.updatePagedUsers();
                    console.log(this.users)
                })
                .catch(error => {
                    console.error('获取用户列表失败:', error);
                });
        },
        editUser(user) {
            this.currentEditUser = { ...user };
            this.editDialogVisible = true;
            console.log(this.currentEditUser);
            console.log(this.editDialogVisible);
        },
        deleteUser(userId) {
            axios.delete(`/user/${userId}`)
                .then(() => {
                    this.fetchUsers(); // 重新获取用户列表
                    this.$message.success('删除用户成功')
                })
                .catch(error => {
                    console.error('删除用户失败:', error);
                });
        },
        submitEdit(user) {
            const id = user.id;
            axios.put(`/user/${id}`, user)
                .then(() => {
                    this.fetchUsers(); // 重新获取用户列表
                    this.$message.success('修改用户成功')
                })
                .catch(error => {
                    console.error('修改用户失败:', error);
                });
            // 发送修改用户的请求
            // 更新成功后关闭对话框并刷新用户列表
            this.editDialogVisible = false;
            this.fetchUsers();
        },
        updatePagedUsers() {
            const startIndex = (this.currentPage - 1) * this.pageSize;
            const endIndex = startIndex + this.pageSize;
            this.pagedUsers = this.users.slice(startIndex, endIndex);
            console.log(this.pagedUsers);
        },
        handleSizeChange(newSize) {
            this.pageSize = newSize;
            this.updatePagedUsers();
        },
        handleCurrentChange(newPage) {
            this.currentPage = newPage;
            this.updatePagedUsers();
        },
        submitReg(registerForm) {
            this.$refs.registerForm.validate((valid) => {
                if (valid) {
                    axios.post('/auth/register', this.registerForm)
                        .then((res) => {
                            console.log(res.data)
                            if (res.data.code === 200) {
                                this.$message.success('新增用户成功');
                                this.showAddUserDialog = false;
                                this.fetchUsers();
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
        this.fetchUsers();
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

.user-management .pagination-container {
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
.user-management{
    margin-left: 50px;
    margin-right: 50px;
    margin-top: 50px;
}
</style>
  
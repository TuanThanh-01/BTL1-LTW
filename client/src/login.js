import 'regenerator-runtime/runtime';
import axios from 'axios';

const emailEl = document.querySelector('#email');
const passwordEl = document.querySelector('#password');
const formLoginEl = document.querySelector('#formLogin');

function clearData() {
  emailEl.value = '';
  passwordEl.value = '';
}

function redirectPage(firstName, lastName) {
  alert(`Xin chào ${firstName} ${lastName}`);
  window.location = 'http://localhost:1234';
}

const sendData = async (data) => {
  try {
    const resp = await axios.post('http://localhost:8082/auth/login', data);
    const fullNameUser = `${resp.data.firstName} ${resp.data.lastName}`;
    localStorage.setItem('jwtToken', resp.data.token);
    localStorage.setItem('userName', fullNameUser);
    clearData();
    redirectPage(resp.data.firstName, resp.data.lastName);
  } catch (err) {
    alert('Sai tài khoản hoặc mật khẩu. Vui lòng đăng nhập lại');
  }
};

formLoginEl.addEventListener('submit', (e) => {
  e.preventDefault();
  const data = {
    email: emailEl.value,
    password: passwordEl.value,
  };
  sendData(data);
});

import 'regenerator-runtime/runtime';
import axios from 'axios';

// làm modal dialog

const formSignUpEle = document.querySelector('#formSignup');
const firstNameEle = document.querySelector('#firstName');
const lastNameEle = document.querySelector('#lastName');
const emailEle = document.querySelector('#email');
const passwordEle = document.querySelector('#password');

let statusCode;
let message;

function clearData() {
  firstNameEle.value = '';
  lastNameEle.value = '';
  emailEle.value = '';
  passwordEle.value = '';
}

function redirectPage(message) {
  alert(message);
  window.location = 'http://localhost:1234/templates/login.html';
}

const sendData = async (data) => {
  try {
    const resp = await axios.post('http://localhost:8082/auth/signup', data);
    statusCode = resp.status;
    message = resp.data.message;
    clearData();
    setTimeout(redirectPage(message), 3000);
  } catch (err) {
    statusCode = err.response.status;
    message = err.response.data.message;
    alert(message);
  }
};

formSignUpEle.addEventListener('submit', (e) => {
  e.preventDefault();
  const data = {
    firstName: firstNameEle.value,
    lastName: lastNameEle.value,
    email: emailEle.value,
    password: passwordEle.value,
  };
  sendData(data);
});

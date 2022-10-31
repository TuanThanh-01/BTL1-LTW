import 'regenerator-runtime/runtime';
import axios from 'axios';

const navBarEl = document.querySelector('#navBar');

if (localStorage.getItem('jwtToken')) {
  const navBar = `
  <a class="navbar-brand font-weight-bold" href="/">Home</a>
  <button
    class="navbar-toggler"
    type="button"
    data-toggle="collapse"
    data-target="#navbarSupportedContent"
    aria-controls="navbarSupportedContent"
    aria-expanded="false"
    aria-label="Toggle navigation"
  >
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Xin chào, ${localStorage.getItem('userName')}
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="#" id="logoutBtn">Log Out</a>
        </div>
      </li>
    </ul>
  </div>`;
  navBarEl.innerHTML = navBar;
}

const formSaveBookEl = document.querySelector('#bookSaveForm');
const titleEl = document.querySelector('#title');
const authorEl = document.querySelector('#author');
const descriptionEl = document.querySelector('#description');
const dateReleaseEl = document.querySelector('#dateRelease');
const totalPageEl = document.querySelector('#totalPage');
const typeBookEl = document.querySelector('#typeBook');

const uploadFileEl = document.querySelector('#uploadFile');
const resultElement = document.querySelector('.preview');
const validImageTypes = ['image/gif', 'image/jpeg', 'image/png'];
const imageFileList = [];

const formData = new FormData();

uploadFileEl.addEventListener('change', function (e) {
  const files = e.target.files;
  for (let i = 0; i < files.length; i++) {
    const file = files[i];
    if (!file.type.match('image')) {
      alert('Sai định dạng, vui lòng chọn ảnh');
      uploadFileEl.value = '';
      if (formData.get('images')) {
        formData.delete('images');
      }
      break;
    }
    formData.append('images', file);
    const fileReader = new FileReader();
    fileReader.readAsDataURL(file);

    fileReader.onload = function () {
      const url = fileReader.result;
      resultElement.insertAdjacentHTML(
        'beforeend',
        `<img src="${url}" alt="${file.name}" class="preview-img"/>`
      );
    };
  }
});

function isValidDate(dateString) {
  const date_regex = /^(0[1-9]|1\d|2\d|3[01])\/(0[1-9]|1[0-2])\/(19|20)\d{2}$/;
  if (!date_regex.test(dateString)) {
    return false;
  }
  return true;
}

formSaveBookEl.addEventListener('submit', (e) => {
  e.preventDefault();
  formData.append('title', titleEl.value);
  formData.append('author', authorEl.value);
  formData.append('description', descriptionEl.value);
  const isTrueDateFormat = isValidDate(dateReleaseEl.value);
  if (isTrueDateFormat === false) {
    alert('Sai định dạng ngày tháng - dd/MM/yyyy');
    console.log(dateReleaseEl.value);

    formData.delete('title');
    formData.delete('author');
    formData.delete('description');
    return false;
  }
  formData.append('dateRelease', dateReleaseEl.value);
  formData.append('totalPage', +totalPageEl.value);
  formData.append('typeBook', typeBookEl.value);

  if (!formData.get('images')) {
    alert('Xin hãy tải ảnh cho sách!!!');
    formData.delete('title');
    formData.delete('author');
    formData.delete('description');
    formData.delete('dateRelease');
    formData.delete('totalPage');
    formData.delete('typeBook');
    return false;
  }

  axios
    .post('http://localhost:8082/book/save', formData, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('jwtToken')}`,
        'Content-Type': 'multipart/form-data',
      },
    })
    .then((res) => {
      setTimeout(alert('Lưu thông tin sách thành công!!!'), 2000);
      window.location = 'http://localhost:1234';
      console.log(res);
    })
    .catch((err) => {
      console.log(err);
    });
});

import 'regenerator-runtime/runtime';
import axios from 'axios';

const tbodyEl = document.querySelector('#tbodyEl');
const tableEl = document.querySelector('#tableHome');
const navBarEl = document.querySelector('#navBar');
const addBookBtnEl = document.querySelector('#addBook');
const actionColumnEl = document.querySelector('#action');

let logoutEl;
let viewBtn;
let deleteBtn;
let books = [];

let url = 'http://localhost:8082/books';

if (localStorage.getItem('jwtToken')) {
  actionColumnEl.classList.remove('hidden');
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
    <form class="form-inline my-2 my-lg-0">
      <input
        id="keywordSearch"
        class="form-control mr-sm-2"
        type="text"
        placeholder="Nhập tên sách..."
      />
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">
        Tìm kiếm
      </button>
    </form>
  </div>`;
  navBarEl.innerHTML = navBar;
  addBookBtnEl.classList.remove('hidden');
}

if (localStorage.getItem('bookData')) {
  localStorage.removeItem('bookData');
}

if (document.querySelector('#logoutBtn')) {
  logoutEl = document.querySelector('#logoutBtn');
  logoutEl.addEventListener('click', () => {
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('userName');
    addBookBtnEl.classList.add('hidden');
    actionColumnEl.classList.add('hidden');
    location.reload();
  });
}

async function getBook() {
  try {
    const data = await axios.get(url);
    books = data.data;
    renderData(books);
    viewBtn = document.querySelectorAll('#btnView');
    deleteBtn = document.querySelectorAll('#btnDelete');
    viewBtn.forEach((item) => {
      item.addEventListener('click', (e) => {
        const index = item.dataset.index;
        localStorage.setItem('bookData', JSON.stringify(books[index]));
        console.log(localStorage.getItem('bookData'));
        console.log(resp.data);
      });
    });
    deleteBtn.forEach((item) => {
      item.addEventListener('click', async (e) => {
        const index = item.dataset.index;
        const id = books[index].id;
        const confirmData = confirm('Bạn có muốn xóa thông tin sách này?');
        if (confirmData) {
          try {
            const resp = await axios.delete(
              `http://localhost:8082/book/delete/${id}`,
              {
                headers: {
                  Authorization: `Bearer ${localStorage.getItem('jwtToken')}`,
                },
              }
            );
            const message = resp.data;
            alert(message);
            setTimeout(location.reload(), 3000);
          } catch (error) {
            console.log(error);
          }
        }
      });
    });
  } catch (error) {
    console.log(error);
  }
}

function renderData(data) {
  let ele = '';
  let countIndex = 0;
  data.map((value) => {
    ele += `<tr>
      <td><img src="http://localhost:8082/images/${
        value.bookImages[0].name
      }" width="80" height="80"/></td>
      <td>${value.title}</td>
      <td>${value.author}</td>
      <td>${value.typeBook}</td>
      <td>${value.dateRelease}</td>
      <td>${value.totalPage}</td>
      ${
        localStorage.getItem('jwtToken')
          ? `<td><a href="/viewBook.html" class="btn btn-success mr-2" id="btnView" data-index=${countIndex}>View</a><button id="btnDelete" data-index=${countIndex} class="btn btn-danger">Delete</button></td>`
          : ''
      }
    </tr>`;
    countIndex++;
  });

  tbodyEl.innerHTML = ele;
}

getBook();
const formSearchEl = document.querySelector('form');
const keywordSearchEl = document.querySelector('#keywordSearch');
formSearchEl.addEventListener('submit', (e) => {
  e.preventDefault();
  url = 'http://localhost:8082/books';
  if (keywordSearchEl.value != '') {
    url = `${url}?keyword=${keywordSearchEl.value}`;
  }
  console.log(url);
  getBook();
});

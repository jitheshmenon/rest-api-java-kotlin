import * as types from '../constants/ActionTypes'
import fetch from 'isomorphic-fetch';

const BASE_URL = 'http://localhost:8080';

export function receiveTodos(todos) {
	return { type: types.RECEIVE_TODOS, todos }
}

export function addTodoDispatch(todo) {
  return { type: types.ADD_TODO, todo }
}

export function deleteTodoDispatch(id) {
  return { type: types.DELETE_TODO, id }
}

export function editTodoDispatch(todo) {
  return { type: types.EDIT_TODO, todo }
}

export function completeTodoDispatch(todo) {
  return { type: types.COMPLETE_TODO, todo }
}

export function completeAllDispatch(todos) {
  return { type: types.COMPLETE_ALL, todos }
}

export function clearCompletedDispatch(todos) {
  return { type: types.CLEAR_COMPLETED, todos }
}

const JSON_HEADERS = { 'Accept': 'application/json', 'Content-Type': 'application/json' };

export function fetchTodos() {
    return function (dispatch) {
	    return fetch(`${BASE_URL}/todos`)
	      .then(response => response.json())
	      .then(json => dispatch(receiveTodos(json)))
  	}
}

export function addTodo(text) {
    return function (dispatch) {
	    return fetch(`${BASE_URL}/todo`,
	    	{
	    		method: 'POST',
	    		headers: JSON_HEADERS,
	    	  	body: JSON.stringify({ text, completed: false })
	    	}
    	  )
	      .then(response => response.json())
	      .then(json => dispatch(addTodoDispatch(json)))
  	}
}

export function deleteTodo(id) {
    return function (dispatch) {
	    return fetch(`${BASE_URL}/todo/` + id,
	    	{
	    		method: 'DELETE',
	    		headers: JSON_HEADERS,
	    	  	body: JSON.stringify({ id })
	    	}
    	  )
	      .then(json => dispatch(deleteTodoDispatch(id)))
  	}
}

export function editTodo(id, text) {
    return function (dispatch) {
	    return fetch(`${BASE_URL}/todo`+ id,
	    	{
	    		method: 'PUT',
	    		headers: JSON_HEADERS,
	    	  	body: JSON.stringify({ id, text })
	    	}
    	  )
	      .then(response => response.json())
	      .then(json => dispatch(editTodoDispatch(json)))
  	}
}

export function completeTodo(id) {
    return function (dispatch) {
	    return fetch(`${BASE_URL}/todo/` + id + `/mark-complete`,
	    	{
	    		method: 'POST',
	    		headers: JSON_HEADERS,
	    	  	body: JSON.stringify({ id })
	    	}
    	  )
	      .then(response => response.json())
	      .then(json => dispatch(editTodoDispatch(json)))
  	}
}

export function completeAll() {
    return function (dispatch) {
	    return fetch(`${BASE_URL}/todos/mark-all-complete`,
	    	{
	    		method: 'POST',
	    		headers: JSON_HEADERS
	    	}
    	  )
	      .then(response => response.json())
	      .then(json => dispatch(completeAllDispatch(json)))
  	}
}

export function clearCompleted() {
    return function (dispatch) {
	    return fetch(`${BASE_URL}/todos/clear`,
	    	{
	    		method: 'DELETE',
	    		headers: JSON_HEADERS
	    	}
    	  )
	      .then(response => response.json())
	      .then(json => dispatch(clearCompletedDispatch(json)))
  	}
}

import React from 'react';
import { render } from 'react-dom';
import { createStore, applyMiddleware } from 'redux';
import { Provider } from 'react-redux';
import thunk from 'redux-thunk';
import { createLogger }from 'redux-logger';
import App from './containers/App';
import reducer from './reducers';
import { fetchTodos } from './actions';
import 'todomvc-app-css/index.css';

require('es6-promise').polyfill();

const store = createStore(reducer,
	applyMiddleware(
	    thunk,
      createLogger()
	)
);

store.dispatch(fetchTodos()).then(() =>
  render(
	  <Provider store={store}>
	    <App />
	  </Provider>,
	  document.getElementById('root')
	)
);



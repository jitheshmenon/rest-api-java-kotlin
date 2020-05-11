import { ADD_TODO, DELETE_TODO, EDIT_TODO, COMPLETE_TODO, COMPLETE_ALL, CLEAR_COMPLETED, RECEIVE_TODOS } from '../constants/ActionTypes';

export default function todos(state = [], action) {
  switch (action.type) {
    case RECEIVE_TODOS:
    case COMPLETE_ALL:
    case CLEAR_COMPLETED:
      state = action.todos;
      return state;
    case ADD_TODO:
      return [
        action.todo,
        ...state
      ];

    case DELETE_TODO:
      return state.filter(todo =>
        todo.id !== action.id
      );

    case EDIT_TODO:
    case COMPLETE_TODO:
      return state.map(todo =>
        todo.id === action.todo.id ?
          action.todo :
          todo
      );
    default:
      return state
  }
}

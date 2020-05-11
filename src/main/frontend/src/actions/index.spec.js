import * as types from '../constants/ActionTypes';
import * as actions from './index';

describe('todo actions', () => {
  it('addTodo should create ADD_TODO action', () => {
    expect(actions.addTodoDispatch('Use Redux')).toEqual({
      type: types.ADD_TODO,
      todo: 'Use Redux'
    })
  });

  it('deleteTodo should create DELETE_TODO action', () => {
    expect(actions.deleteTodoDispatch(1)).toEqual({
      type: types.DELETE_TODO,
      id: 1
    })
  });

  it('editTodo should create EDIT_TODO action', () => {
    expect(actions.editTodoDispatch({ id: 1, todo: 'Use Redux everywhere' })).toEqual({
      type: types.EDIT_TODO,
      todo: {
        id: 1,
        todo: 'Use Redux everywhere'
      }
    })
  });

  it('completeTodo should create COMPLETE_TODO action', () => {
    expect(actions.completeTodoDispatch({ id: 1 })).toEqual({
      type: types.COMPLETE_TODO,
      todo: {
        id: 1
      }
    })
  });

  it('completeAll should create COMPLETE_ALL action', () => {
    expect(actions.completeAllDispatch()).toEqual({
      type: types.COMPLETE_ALL
    })
  });

  it('clearCompleted should create CLEAR_COMPLETED action', () => {
    expect(actions.clearCompletedDispatch()).toEqual({
      type: types.CLEAR_COMPLETED
    })
  })
});

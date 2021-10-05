import { createAction, createReducer } from '@reduxjs/toolkit';

interface UserState {
    value: number;
}

const increment = createAction('user/increment');
const decrement = createAction('user/decrement');

const initialState = { value: 0 } as UserState;

export const userReducer = createReducer(initialState, (builder) => {
    builder
        .addCase(increment, (state, action) => {
            state.value++;
        })
        .addCase(decrement, (state, action) => {
            state.value--;
        });
});

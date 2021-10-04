const userReducer = (state = [], action: any) => {
    switch (action.type) {
        case action.type === 'SAVE_USER':
            return action.data;
        default:
            return state;
    }
};

export default userReducer;

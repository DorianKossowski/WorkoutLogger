const handle = (error, msg) => {
    if (error.data) {
        if(error.status === 500) {
            return msg + 'Interal error'
        }
        return msg + error.data.message;
    } else {
        return msg + error;
    }
}

export default handle;
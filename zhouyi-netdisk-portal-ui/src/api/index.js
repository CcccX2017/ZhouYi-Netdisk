import request from "../utils/request";

let base = ''

// post请求
export const postRequest = (url, params) => {
    return request({
        method: 'POST',
        url: `${base}${url}`,
        data: params
    })
}

// put请求
export const putRequest = (url, params) => {
    return request({
        method: 'PUT',
        url: `${base}${url}`,
        data: params
    })
}

// delete请求
export const deleteRequest = (url, params) => {
    return request({
        method: 'DELETE',
        url: `${base}${url}`,
        data: params
    })
}

// get请求
export const getRequest = (url, params) => {
    return request({
        method: 'GET',
        url: `${base}${url}`,
        params: params
    })
}

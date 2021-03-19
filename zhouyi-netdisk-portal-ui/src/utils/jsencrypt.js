import JSEncrypt from 'jsencrypt/bin/jsencrypt.min'

const publicKey = 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC5eB2RFmcC3yaAsT2dXi6mVLWV\n' +
    'tpEBcXbj+xVLuoQ2btIHvIpXNEOWsbpTCP2mD/PjMvRaIeutuxMha/IVXUeObPxx\n' +
    'TrPaSC6F67ew1hyVEQ59DaQ1lXEFqoIrOu5u/fifev884cIBzz8PbSZyIA5PA56J\n' +
    'vV4+sMr/GmyBKANiKQIDAQAB'

const privateKey = 'MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALl4HZEWZwLfJoCx\n' +
    'PZ1eLqZUtZW2kQFxduP7FUu6hDZu0ge8ilc0Q5axulMI/aYP8+My9Foh6627EyFr\n' +
    '8hVdR45s/HFOs9pILoXrt7DWHJURDn0NpDWVcQWqgis67m79+J96/zzhwgHPPw9t\n' +
    'JnIgDk8Dnom9Xj6wyv8abIEoA2IpAgMBAAECgYBWIX0Bmvi9sansy+LESS+62M33\n' +
    'N/CBThMbbsgGAhlSOgbZxuor8z6MJf9UfnFlBDWLxmcJ0ITlimIo/DtRRp9qr6Ps\n' +
    '/sw66t/Sw6Ecs+yzslUdxSvzWUz6eL8s7kweCfy/7Mhoz1/TQxs3Pf1JQskoqV3N\n' +
    'hh6gra2c8l0zuRf/gQJBAOBM4zCLYZqBr9e5JZdmBZqGAqfNVxhooGNo730CKW85\n' +
    'vPumcxbZsVEvoaAbauoqIkZ+x7g7YLn+oEqOd6McCrECQQDTrlSE4l1zL0Y9abXb\n' +
    'Td4GYKQZVPWkjhiiugCoDY+iuXOl4GIIIwuwIQ5jmiU/NSOKbMGotfd9b8f6wXI6\n' +
    '07z5AkAHQzYP+lNct1pEN+B5uHnx+OjZGhL53VlkHp+ygoFo8oul8kvDr6TIJiyr\n' +
    'jHe3wUl+0tgQpQuKui3ifB5vj5bhAkAE13jxbSgMCWnG39McCATrCmzWc34G3IUt\n' +
    'u6lmLqE5bJp7kc1MOFj9F1R37L+rWXDNqr0nObpGcTcWwjP1qoApAkEAqdbqz86c\n' +
    'z/tGJutBT4YRc0UYIa0nFRSB7Ce9u4sz0oA7EUjR4liIi7pCkQvfcf4Ty/2cNcl9\n' +
    'jsqogGX9+vU1tA=='

// 加密
export function encrypt(txt) {
    const encryptor = new JSEncrypt()
    encryptor.setPublicKey(publicKey) // 设置公钥
    return encryptor.encrypt(txt) // 对数据进行加密
}

// 解密
export function decrypt(txt) {
    const encryptor = new JSEncrypt()
    encryptor.setPrivateKey(privateKey) // 设置私钥
    return encryptor.decrypt(txt) // 对数据进行解密
}

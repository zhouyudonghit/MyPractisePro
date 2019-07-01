// IBookManager.aidl
package com.example.yudongzhou.remoteservice;

// Declare any non-default types here with import statements

import com.example.yudongzhou.remoteservice.bean.Book;
interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}

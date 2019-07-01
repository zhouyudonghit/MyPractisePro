// IBookManager.aidl
package com.example.localuser.retrofittest.aidl;

// Declare any non-default types here with import statements

import com.example.localuser.retrofittest.aidl.Book;
interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}

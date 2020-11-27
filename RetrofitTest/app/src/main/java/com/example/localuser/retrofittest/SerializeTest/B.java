package com.example.localuser.retrofittest.SerializeTest;

import android.os.Parcel;
import android.os.Parcelable;

public class B extends A implements Parcelable {
    protected int b;

    public B()
    {

    }

    protected B(Parcel in) {
        b = in.readInt();
    }

    public static final Creator<B> CREATOR = new Creator<B>() {
        @Override
        public B createFromParcel(Parcel in) {
            return new B(in);
        }

        @Override
        public B[] newArray(int size) {
            return new B[size];
        }
    };

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(a);
        dest.writeInt(b);
    }

    @Override
    public String toString() {
        return super.toString()+", b = "+b;
    }
}

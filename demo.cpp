#include<iostream>
#include<stdlib.h>

int top=-1;

class Stack
{
	public:
		int arr[10];
		void push(int);
};

void Stack::push(int num)
{
	if(top>=10)
	{
		cout<<"Return it out ";
		return;
	}else{
	top++;
	arr[top]=num;
	}
}

#include<iostream>
#include<stdlib.h>

int top=-1;

class Stack
{
	public:
		int arr[10];
		void push(int);
		void display();
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

void Stack::display()
{
	for(int i=0;i<top;i++)
	{
		cout<<arr[top]<<endl;
	}
}

int main()
{
	int num;
	Stack s;
	
	cout<<"Enter a number to push ";
	cin>>num;
	
	s.push(num);
	s.display();
	return 0;
}


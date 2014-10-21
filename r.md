	Data structures
		Vector
			c(v1, v2, v3,...) # creates a collection/vector. Vector of vectors it flattened. No mixed types allowed.
		List
			# mixed types allowed, lists are recursive, lists are vectors
			alist <- list(c("a", "b", "c"), c(1,2,3,4), c(8e6, 5.2e9, -9.3e7))
			alist[[1]] # return one column
			alist[1] # extracts a list
			alist[[3:2]] # gets 2nd element from 3rd vector of the list (5.2e9)
			is.list(alist) #TRUE
			is.vector(alist) #TRUE
		Data Frame
			# All columns in data frame must have names
			data.frame() # create data frame or table e.g., test.data.frame<-data.frame(id=c(1,2,3,4,5),name=c("a","b","c","d","e"))
			edit(framename) # edit the content of the table
			str(frame) # prints the structure and data types of the data frame
			names(framename) – prints the column names
			dataframe[index.ROW, index.COLUMN]
			dataframe[[1]] # returns the first column as a vector
			dataframe[1] # returns the first column wrapped in a data frame
			dataframe[c(1,3)] # returns the 1st and 3rd column wrapped in a data frame
			dataframe[1:3,] # displays all columns but first 1-3 rows only
			is.data.frame(framename) #checks if an object is a data frame
		Factor

	Reading
		Read from file
			read.table # space is the default delimiter
			read.csv # by default expects a file header
			read.csv2, read.delim, read.delim2
		Read from database

		Read from URL

	Basics Statistic Functions
		mean(x) # x is a vector. 
		median(x)
		sd(x)
		var(x)
		cor(x,y)
		cov(x,y)
		lapply(dataframe, function) # Apply function like mean/median over a list/dataframe

	Graphs
		Scatter Plot
			* what? to see the relationship between 2 parallel vectors
			* when to use?
			* How? plot(x,y) # only numeric vectors or dataframes are allowed.
		Bar Chart
			* when to use?
			* How? barplot()
		Box Plot
			* what? provides a quick visual summary of a dataset. Thick line in middle is the median. Box identifies the 1st(bottom) and 3rd(top) quartiles.
			* when to use?
			* How? boxplot()
		Histogram
			* what? Groups data into bins
			* when to use?
			* How? hist(x) where x is a vector

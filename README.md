# **Lab 3: HashMap Implementation and Performance Evaluation**

This repository contains the solution for **Lab 3** of the course, which focuses on implementing various methods for a custom `HashMap` and evaluating the performance of its operations. The task involves completing methods in two classes, **HashMap** and **HashMapOa**, and comparing the execution time of selected methods through **JMH** benchmarking.

## **Project Overview**

The primary objectives of this project are:
1. **Method Implementation**:
   - Implement missing methods in the `HashMap` and `HashMapOa` classes:
     - `remove(K key)`: Removes the data point with the given key.
     - `containsValue(Object value)`: Checks if one or more keys for the specified value exist in the hash table.
     - `replace(K key, V oldValue, V newValue)`: Replaces an existing value for a given key.

2. **Performance Testing**:
   - Conduct speed tests using **JMH (Java Microbenchmarking Harness)** to compare the performance of various methods in `HashMap` and `HashMapOa` classes. These tests will measure execution time across a range of input data sizes.

3. **Asymptotic Complexity and Analysis**:
   - Theoretical analysis of the time complexity of the implemented methods.
   - Empirical testing to verify if the observed performance aligns with theoretical expectations.

### **Implemented Methods**
The following methods were implemented and tested:
- `remove(K key)`
- `containsValue(Object value)`
- `replace(K key, V oldValue, V newValue)`

These methods are essential for managing key-value pairs in the hash table, supporting operations like deletion, value checking, and value replacement.

### **Performance Testing**
JMH was used to benchmark the following method pairs:
- **`remove()`** in both `HashMap` and `HashMapOa` classes.
- **`contains()`** in both `HashMap` and `HashMapOa`.
- **`put()`**, **`get()`**, and **`containsValue()`** in both custom and `java.util.HashMap`.

The performance tests are designed to determine the impact of different implementations on the execution time, particularly as the size of input data increases.

### **Speed Testing Methodology**
1. **JMH Benchmarking**: 
   - Each pair of methods was benchmarked separately using **JMH**.
   - The tests were run across a range of input sizes, and execution times were recorded for each run.
   
2. **Test Setup**:
   - The tests were executed on a computer with the following configuration:
     - **Processor**: Intel i7-9700K (8 cores, 3.6 GHz)
     - **RAM**: 16 GB DDR4
     - **OS**: Windows 10 64-bit
     - **Java Version**: OpenJDK 11

3. **Graphing Performance**:
   - A graph was plotted showing the relationship between the input size and the execution time for each method pair. This graph helps visualize the algorithm's scalability.

### **Asymptotic Complexity**
- **`remove()`**: The expected time complexity is **O(1)** for `HashMap` and **O(n)** for `HashMapOa` due to the nature of their implementations (open addressing vs. separate chaining).
- **`containsValue()`**: Expected time complexity is **O(n)** for both classes as it requires scanning the entire table in the worst case.
- **`replace()`**: Expected time complexity is **O(1)** for `HashMap` and **O(n)** for `HashMapOa`.

### **Conclusions**
- **Execution Time vs. Theoretical Complexity**: The experimentally determined time complexity aligns closely with the theoretical expectations. However, real-world performance may slightly vary due to factors like collisions and hash function efficiency.
- **Best Method**: The `remove()` and `containsValue()` methods in `HashMap` (using separate chaining) tend to perform better in most cases compared to the open-addressing approach used in `HashMapOa`, particularly as the input size increases.

### **Additional Implemented Methods (Optional)**
During the defense, the following additional methods may be implemented or explained:
- `keySet()`: Returns a set of all keys.
- `values()`: Returns a list of all values.
- `getNumberOfCollisions()`: Returns the number of collisions in the hash table.
- `averageChainSize()`: Returns the average length of chains in the hash table.
- `replace(K key, V value)`: Replaces the value for the given key.
- `replaceAll(V oldValue, V newValue)`: Replaces all occurrences of the old value with a new value.
- `putIfAbsent(K key, V value)`: Inserts a key-value pair only if the key is not already present.

### **How to Run**
1. Clone this repository to your local machine.
2. Open the project in your preferred IDE (e.g., IntelliJ IDEA or Eclipse).
3. Implement the missing methods if needed.
4. Run the JMH benchmarks to evaluate the performance of the different methods.
5. Review the results in the console output and check the generated performance graphs.


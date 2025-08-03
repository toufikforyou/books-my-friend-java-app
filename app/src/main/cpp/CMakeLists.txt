# Minimum required CMake version
cmake_minimum_required(VERSION 3.22)

# Set the project name and specify languages
project(MyNativeLibrary CXX)

# Add your C/C++ source files here
add_library(MyNativeLibrary SHARED
        api.cpp
        api_token.cpp
        authentication.cpp
        location.cpp
        )

# Specify include directories (if needed)
#target_include_directories(MyNativeLibrary PRIVATE
#        src/main/cpp/include
#        )

# Set the ABI(s) for the library
set_target_properties(MyNativeLibrary PROPERTIES
        CXX_STANDARD 11
        CXX_STANDARD_REQUIRED YES
        CXX_EXTENSIONS NO
        )

# Link any libraries (if needed)
# target_link_libraries(MyNativeLibrary PRIVATE
#     log
# )

# Specify any other CMake settings and configurations as required.
# For example, you can set compile flags, optimizations, etc.

# Specify the directory where the built shared libraries will be placed
# (This is usually automatically set by the Android Gradle plugin)
#set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI})

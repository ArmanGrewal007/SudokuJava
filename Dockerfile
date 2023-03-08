FROM ubuntu:latest

MAINTAINER ArmanGrewal007 <armancode4u@gmail.com>

# Install XQuartz
# RUN brew install --cask xquartz
RUN \
    apt-get update -y && \
    apt-get install default-jre -y

# Set the DISPLAY environment variable
ENV DISPLAY=host.docker.internal:0

# Set the working directory
WORKDIR /app

# Copy the application files
COPY . .

# Start the application
CMD ["java", "SudokuMain"]

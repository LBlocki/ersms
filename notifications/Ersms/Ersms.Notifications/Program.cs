using Azure.Messaging.ServiceBus;

// the client that owns the connection and can be used to create senders and receivers
ServiceBusClient client;

// the sender used to publish messages to the queue
ServiceBusSender sender;

// number of messages to be sent to the queue
const int numOfMessages = 3;

// The Service Bus client types are safe to cache and use as a singleton for the lifetime
// of the application, which is best practice when messages are being published or read
// regularly.
//
// set the transport type to AmqpWebSockets so that the ServiceBusClient uses the port 443. 
// If you use the default AmqpTcp, you will need to make sure that the ports 5671 and 5672 are open

// TODO: Replace the <NAMESPACE-CONNECTION-STRING> and <QUEUE-NAME> placeholders
var clientOptions = new ServiceBusClientOptions()
{ 
    TransportType = ServiceBusTransportType.AmqpWebSockets
};
client = new ServiceBusClient("<FILL>", clientOptions);
sender = client.CreateSender("notifications");

// create a batch 
using ServiceBusMessageBatch messageBatch = await sender.CreateMessageBatchAsync();

for (int i = 1; i <= numOfMessages; i++)
{
    // try adding a message to the batch
    if (!messageBatch.TryAddMessage(new ServiceBusMessage($"Message {i}")))
    {
        // if it is too large for the batch
        throw new Exception($"The message {i} is too large to fit in the batch.");
    }
}

try
{
    // Use the producer client to send the batch of messages to the Service Bus queue
    await sender.SendMessagesAsync(messageBatch);
    Console.WriteLine($"A batch of {numOfMessages} messages has been published to the queue.");
}
finally
{
    // Calling DisposeAsync on client types is required to ensure that network
    // resources and other unmanaged objects are properly cleaned up.
    await sender.DisposeAsync();
    await client.DisposeAsync();
}

Console.WriteLine("Press any key to end the application");
Console.ReadKey();

// // https://learn.microsoft.com/en-us/azure/service-bus-messaging/service-bus-dotnet-get-started-with-queues?tabs=connection-string#receive-messages-from-the-queue
// using Azure.Messaging.ServiceBus;
// using System;
// using System.Threading.Tasks;
//
// // the client that owns the connection and can be used to create senders and receivers
// ServiceBusClient client;
//
// // the processor that reads and processes messages from the queue
// ServiceBusProcessor processor;
//
// // The Service Bus client types are safe to cache and use as a singleton for the lifetime
// // of the application, which is best practice when messages are being published or read
// // regularly.
// //
// // Set the transport type to AmqpWebSockets so that the ServiceBusClient uses port 443. 
// // If you use the default AmqpTcp, make sure that ports 5671 and 5672 are open.
//
// var serviceBusConnectionString = "<FILL>";
// var queueName = "notifications";
// var emailServiceConnectionString = "<FILL>";
//
// var clientOptions = new ServiceBusClientOptions()
// {
//     TransportType = ServiceBusTransportType.AmqpWebSockets
// };
// client = new ServiceBusClient(serviceBusConnectionString, clientOptions);
//
// // create a processor that we can use to process the messages
// processor = client.CreateProcessor(queueName, new ServiceBusProcessorOptions());
//
// try
// {
//     // add handler to process messages
//     processor.ProcessMessageAsync += MessageHandler;
//
//     // add handler to process any errors
//     processor.ProcessErrorAsync += ErrorHandler;
//
//     // start processing 
//     await processor.StartProcessingAsync();
//
//     Console.WriteLine("Wait for a minute and then press any key to end the processing");
//     Console.ReadKey();
//
//     // stop processing 
//     Console.WriteLine("\nStopping the receiver...");
//     await processor.StopProcessingAsync();
//     Console.WriteLine("Stopped receiving messages");
// }
// finally
// {
//     // Calling DisposeAsync on client types is required to ensure that network
//     // resources and other unmanaged objects are properly cleaned up.
//     await processor.DisposeAsync();
//     await client.DisposeAsync();
// }
//
// // handle received messages
// async Task MessageHandler(ProcessMessageEventArgs args)
// {
//     string body = args.Message.Body.ToString();
//     Console.WriteLine($"Received: {body}");
//
//     // complete the message. message is deleted from the queue. 
//     await args.CompleteMessageAsync(args.Message);
// }
//
// // https://learn.microsoft.com/en-us/azure/communication-services/quickstarts/email/send-email?tabs=windows%2Cconnection-string&pivots=programming-language-csharp
// async Task SendEmail(string recipient)
// {
//     var sender = "<FILL>";
//     var subject = "Zamówienie";
//     var content = """
//     <html>
//         <body>
//             <h1>Hi there!</h1>
//             <p>This is a test email from the Azure Service Bus queue.</p>
//         </body>
//     </html>
//     """;
//     // TODO: send email
// }
//
// // handle any errors when receiving messages
// Task ErrorHandler(ProcessErrorEventArgs args)
// {
//     Console.WriteLine(args.Exception.ToString());
//     return Task.CompletedTask;
// }